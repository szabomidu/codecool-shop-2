package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    private List<Order> data = new ArrayList<>();
    private static OrderDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public int add(Order order) {
        order.setId(data.size() + 1);
        data.add(order);
        return order.getId();
    }

    @Override
    public void addAll(Order... orders) {
        Arrays.stream(orders).forEach(this::add);
    }

    @Override
    public Order find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Order> getAll() {
        return data;
    }

    @Override
    public void clearData() {
        data.clear();
    }
  
    @Override
    public void update(Order order) {

    }
}
