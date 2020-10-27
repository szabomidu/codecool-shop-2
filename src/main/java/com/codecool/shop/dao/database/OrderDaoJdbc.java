package com.codecool.shop.dao.database;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;
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

    }

    @Override
    public Order find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Order> getAll() {
        return null;
    }
}
