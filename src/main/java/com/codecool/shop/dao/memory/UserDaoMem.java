package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoMem implements UserDao {
    private List<User> data = new ArrayList<>();
    private static UserDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private UserDaoMem() {
    }

    public static UserDaoMem getInstance() {
        if (instance == null) {
            instance = new UserDaoMem();
        }
        return instance;
    }

    @Override
    public int add(User user) {
        user.setId(data.size() + 1);
        data.add(user);
        return user.getId();
    }

    @Override
    public void addAll(User... users) {
        Arrays.stream(users).forEach(this::add);
    }

    @Override
    public User find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<User> getAll() {
        return data;
    }

    @Override
    public User getBy(Order order) {
        return data.stream().filter(t -> t.getOrders().contains(order)).findFirst().orElse(null);
    }

    @Override
    public void clearData() {
        data.clear();
    }
}
