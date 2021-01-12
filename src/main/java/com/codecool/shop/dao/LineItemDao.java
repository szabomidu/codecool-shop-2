package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.util.List;

public interface LineItemDao {
    void add(LineItem lineItem);
    void addAll(LineItem ... lineItems);
    LineItem find(int id);
    void remove(int id);
    void update(LineItem lineItem, int change);

    List<LineItem> getAll();
    void clearData();
    LineItem getBy(Order order, Product product);
    List<LineItem> getBy(Order order);
}
