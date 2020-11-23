package com.codecool.shop.dao.database;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private DataSource dataSource = ConnectionHandler.getDataSource();
    private static UserDaoJdbc instance = null;

    private UserDaoJdbc() throws SQLException {
    }

    public UserDaoJdbc(DataSource dataSource) throws SQLException {
    	this.dataSource = dataSource;
	}

    public static UserDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserDaoJdbc();
        }
        return instance;
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
            throw new RuntimeException("Error while adding new User.", throwable);
        }
    }

    @Override
    public void addAll(User... users) {
        Arrays.stream(users).forEach(this::add);
    }

    @Override
    public User find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name FROM \"user\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            User user = new User(rs.getString(2));
            user.setId(id);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding User.", e);
        }
    }

    @Override
    public void remove(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM \"user\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting USer.", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name FROM \"user\"";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<User> users = new ArrayList<>();

            while (rs.next()) {
                User user = new User(rs.getString(2));
                user.setId(rs.getInt(1));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding User.", e);
        }
    }

    @Override
    public User getBy(Order order) {
        return find(order.getUserId());
    }

    @Override
    public void clearData() {
    }
}
