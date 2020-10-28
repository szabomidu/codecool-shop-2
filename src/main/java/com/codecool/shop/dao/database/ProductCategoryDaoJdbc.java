package com.codecool.shop.dao.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private DataSource dataSource = ConnectionHandler.getDataSource();
    private ProductCategoryDao productCategoryDao;
    private static ProductCategoryDaoJdbc instance = null;


    private ProductCategoryDaoJdbc() throws SQLException {
    }

    public static ProductCategoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product_category (name, description) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, category.getName());
            st.setString(2, category.getDescription());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explanation
            category.setId(rs.getInt(1));

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Category.", throwable);
        }
    }

    @Override
    public void addAll(ProductCategory... categories) {
        Arrays.stream(categories).forEach(this::add);
    }

    @Override
    public ProductCategory find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, description FROM product_category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            ProductCategory productCategory = new ProductCategory(rs.getString(1), rs.getString(2), "Figure");
            productCategory.setId(id);
            return productCategory;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Category.", e);
        }
    }

    @Override
    public ProductCategory findByName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM product_category WHERE name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            ProductCategory productCategory  = productCategoryDao.find(rs.getInt(1));
            return productCategory;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Category by name.", e);
        }
    }

    @Override
    public void remove(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM product_category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while deleting Category.", throwable);
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM product";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<ProductCategory> categories = new ArrayList<>();

            while (rs.next()) {
                ProductCategory productCategory  = productCategoryDao.find(rs.getInt(1));
                categories.add(productCategory);
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all Categories.", e);
        }
    }
}
