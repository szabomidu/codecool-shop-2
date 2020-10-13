package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    void addAll(User ... users);
    User find(int id);
    void remove(int id);

    List<User> getAll();
    User getBy(Order order);
}
