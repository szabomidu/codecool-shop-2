package com.codecool.shop.dao.database;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDaoJdbc implements OrderDao {

    private final DataSource dataSource = ConnectionHandler.getDataSource();
    private static OrderDaoJdbc instance = null;

    private OrderDaoJdbc() throws SQLException {
    }

    public static OrderDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderDaoJdbc();
        }
        return instance;
    }

    @Override
    public int add(Order order) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO \"order\" (user_id) VALUES (?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, order.getUserId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            order.setId(id);
            return id;
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Order.", throwable);
        }
    }

    @Override
    public void addAll(Order... orders) {
        Arrays.stream(orders).forEach(this::add);
    }

    @Override
    public Order find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, user_id, first_name, last_name, email, address, zip_code, city, country FROM \"order\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Order order = new Order(rs.getInt(2));
            order.setId(id);
            OrderData orderData = new OrderData(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9));
            order.saveData(orderData);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Order.", e);
        }
    }

    @Override
    public void remove(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM \"order\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while deleting Order.", throwable);
        }
    }

    @Override
    public List<Order> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, user_id, first_name, last_name, email, address, zip_code, city, country FROM \"order\"";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<Order> orders = new ArrayList<>();

            while (rs.next()) {
                Order order = new Order(rs.getInt(2));
                order.setId(rs.getInt(1));
                OrderData orderData = new OrderData(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9));
                order.saveData(orderData);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Order.", e);
        }
    }

    @Override
    public void clearData() {

    }

    @Override
    public void update(Order order) {
        try {
            Connection connection = dataSource.getConnection();
            String query = "UPDATE \"order\" SET first_name = ?, last_name = ?, email = ?, address = ?, zip_code = ?, city = ?, country = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, order.getFirstName());
            statement.setString(2, order.getLastName());
            statement.setString(3, order.getEmail());
            statement.setString(4, order.getAddress());
            statement.setInt(5, order.getZipCode());;
            statement.setString(6, order.getCity());
            statement.setString(7, order.getCountry());
            statement.setInt(8, order.getId());

            statement.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while updating order in table", throwable);
        }
    }
}
