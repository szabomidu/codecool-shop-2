package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseModel {
    private List<Order> orders;

    public User(String name) {
        super(name);
        orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
