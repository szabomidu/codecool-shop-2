package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineItemDaoMem implements LineItemDao {
    private List<LineItem> data = new ArrayList<>();
    private static LineItemDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private LineItemDaoMem() {
    }

    public static LineItemDaoMem getInstance() {
        if (instance == null) {
            instance = new LineItemDaoMem();
        }
        return instance;
    }

    @Override
    public void add(LineItem lineItem) {
        lineItem.setId(data.size() + 1);
        data.add(lineItem);
    }

    @Override
    public void addAll(LineItem... lineItems) {
        Arrays.stream(lineItems).forEach(this::add);
    }

    @Override
    public LineItem find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void update(LineItem lineItem, int change) {
            if (lineItem.getQuantity() + change <= 0) throw new IllegalArgumentException();
            lineItem.changeQuantity(change);
    }

    @Override
    public List<LineItem> getAll() {
        return data;
    }

    @Override
    public void clearData() {
        data.clear();
    }
  
    @Override
    public LineItem getBy(Order order, Product product) {
        return order.findLineItemForProduct(product);
    }

    @Override
    public List<LineItem> getBy(Order order) {
        return order.getLineItems();
    }
}
