package com.codecool.shop.dao.database;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private final DataSource dataSource = ConnectionHandler.getDataSource();

    public ProductDaoJdbc() throws SQLException {
    }

    @Override
    public void add(Product product) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product (name, description, price, currency, supplier_id, category_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setFloat(3, product.getDefaultPrice());
            st.setString(4, product.getDefaultCurrency().toString());
            st.setInt(5, product.getSupplier().getId());
            st.setInt(6, product.getProductCategory().getId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explanation
            product.setId(rs.getInt(1));

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Product.", throwable);
        }
    }

    @Override
    public void addAll(Product... products) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier, ProductCategory productCategory) {
        return null;
    }
}
