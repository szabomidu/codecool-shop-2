package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.List;

public interface OrderDao {
    int add(Order order);
    void addAll(Order ... orders);
    Order find(int id);
    void remove(int id);

    List<Order> getAll();

    void clearData();
}

