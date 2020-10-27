package com.codecool.shop.dao.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private final DataSource dataSource = ConnectionHandler.getDataSource();
    private final SupplierDao supplierDao = new SupplierDaoJdbc();
    private final ProductCategoryDao productCategoryDao = new ProductCategoryDaoJdbc();


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
        Arrays.stream(products).forEach(this::add);
    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, price, currency, supplier_id, category_id FROM product WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            Supplier supplier = supplierDao.find(rs.getInt(6));
            ProductCategory productCategory  = productCategoryDao.find(rs.getInt(5));
            Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(5), rs.getString(3), productCategory, supplier);
            product.setId(id);
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Product.", e);
        }
    }

    @Override
    public void remove(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM product WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while deleting Product.", throwable);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, price, currency, supplier_id, category_id FROM product";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                Supplier supplier = supplierDao.find(rs.getInt(6));
                ProductCategory productCategory  = productCategoryDao.find(rs.getInt(5));
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(5), rs.getString(3), productCategory, supplier);
                product.setId(rs.getInt(1));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Product.", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, price, currency, supplier_id, category_id FROM product WHERE supplier_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, supplier.getId());
            ResultSet rs = st.executeQuery();
            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                ProductCategory productCategory  = productCategoryDao.find(rs.getInt(5));
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(5), rs.getString(3), productCategory, supplier);
                product.setId(rs.getInt(1));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Product.", e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, price, currency, supplier_id, category_id FROM product WHERE category_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, productCategory.getId());
            ResultSet rs = st.executeQuery();
            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                Supplier supplier = supplierDao.find(rs.getInt(6));
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(5), rs.getString(3), productCategory, supplier);
                product.setId(rs.getInt(1));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Product.", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier, ProductCategory productCategory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, price, currency, supplier_id, category_id FROM product WHERE category_id = ? AND supplier_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, productCategory.getId());
            st.setInt(2, supplier.getId());
            ResultSet rs = st.executeQuery();
            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                Product product = new Product(rs.getString(2), rs.getFloat(4), rs.getString(5), rs.getString(3), productCategory, supplier);
                product.setId(rs.getInt(1));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Product.", e);
        }
    }
}
