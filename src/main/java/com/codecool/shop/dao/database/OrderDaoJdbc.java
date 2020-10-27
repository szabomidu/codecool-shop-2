package com.codecool.shop.dao.database;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class OrderDaoJdbc implements OrderDao {

    private final DataSource dataSource = ConnectionHandler.getDataSource();

    public OrderDaoJdbc() throws SQLException {
    }

    @Override
    public int add(Order order) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO order (user_id) VALUES (?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, order.getUserId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            order.setId(id);
            return id;
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Product.", throwable);
        }
    }

    @Override
    public void addAll(Order... orders) {
        Arrays.stream(orders).forEach(this::add);
    }

    @Override
    public Order find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, user_id, first_name, last_name, email, address, zip_code, city, country FROM order WHERE id = ?";
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
            throw new RuntimeException("Error while finding Product.", e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Order> getAll() {
        return null;
    }
}
