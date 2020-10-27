package com.codecool.shop.dao.database;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private final DataSource dataSource = ConnectionHandler.getDataSource();

    public UserDaoJdbc() throws SQLException {
    }

    @Override
    public int add(User user) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO \"user\" (name) VALUES (?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getName());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explanation
            int id = rs.getInt(1);
            user.setId(id);
            return id;
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Product.", throwable);
        }
    }

    @Override
    public void addAll(User... users) {

    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getBy(Order order) {
        return null;
    }
}
