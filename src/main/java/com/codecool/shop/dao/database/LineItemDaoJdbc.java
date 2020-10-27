package com.codecool.shop.dao.database;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineItemDaoJdbc implements LineItemDao {

    private DataSource dataSource = ConnectionHandler.getDataSource();

    public LineItemDaoJdbc() throws SQLException {
    }

    @Override
    public void add(LineItem lineItem) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO lineitem (order_id, product_id, total_price, unit_price, quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, lineItem.getOrderId());
            st.setInt(2, lineItem.getProductId());
            st.setFloat(3, lineItem.getTotalPrice());
            st.setFloat(4, lineItem.getUnitPrice());
            st.setInt(5, lineItem.getQuantity());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            lineItem.setId(rs.getInt(1));
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new LineItem.", throwable);
        }
    }

    @Override
    public void addAll(LineItem... lineItems) {
        Arrays.stream(lineItems).forEach(this::add);
    }

    @Override
    public LineItem find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, order_id, product_id, total_price, unit_price, quantity FROM lineitem WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Order order = OrderDao.find(rs.getInt(2));
            Product product = ProductDao.find(rs.getInt(3));
            LineItem lineItem = new LineItem(product, order.getId());
            product.setId(id);
            return lineItem;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding LineItem.", e);
        }
    }

    @Override
    public void remove(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM lineitem WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while deleting LineItem.", throwable);
        }

    }

    @Override
    public List<LineItem> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, order_id, product_id, total_price, unit_price, quantity FROM lineitem";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<LineItem> lineItems = new ArrayList<>();

            while (rs.next()) {
                Order order = OrderDao.find(rs.getInt(2));
                Product product = ProductDao.find(rs.getInt(3));
                LineItem lineItem = new LineItem(product, order.getId());
                lineItem.setId(rs.getInt(1));
                lineItems.add(lineItem);
            }
            return lineItems;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding LineItem.", e);
        }    }
}
