package com.codecool.shop.dao.database;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {


    private DataSource dataSource = ConnectionHandler.getDataSource();

    public SupplierDaoJdbc() throws SQLException {
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public void addAll(Supplier... suppliers) {

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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier findByName(String name) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }
}