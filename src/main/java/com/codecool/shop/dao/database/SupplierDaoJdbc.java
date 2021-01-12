package com.codecool.shop.dao.database;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {


    private DataSource dataSource = ConnectionHandler.getDataSource();
    private static SupplierDaoJdbc instance = null;

    private SupplierDaoJdbc() throws SQLException {
    }

    public static SupplierDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO supplier (name, description) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, supplier.getName());
            st.setString(2, supplier.getDescription());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explanation
            supplier.setId(rs.getInt(1));
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Supplier.", throwable);
        }
    }

    @Override
    public void addAll(Supplier... suppliers) {
        Arrays.stream(suppliers).forEach(this::add);
    }

    @Override
    public Supplier find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, description FROM supplier WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            Supplier supplier = new Supplier(rs.getString(1), rs.getString(2));
            supplier.setId(id);
            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Supplier.", e);
        }
    }

    @Override
    public Supplier findByName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM supplier WHERE name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
            supplier.setId(rs.getInt(1));
            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Supplier.", e);
        }
    }

    @Override
    public void remove(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM supplier WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while deleting Supplier.", throwable);
        }

    }

    @Override
    public List<Supplier> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM supplier";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Supplier> suppliers = new ArrayList<>();
            while (rs.next()) {
                Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
                supplier.setId(rs.getInt(1));
                suppliers.add(supplier);
            }
            return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Product.", e);
        }
    }

    @Override
    public void clearData() {

    }
}
