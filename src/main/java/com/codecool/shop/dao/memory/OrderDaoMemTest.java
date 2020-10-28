package com.codecool.shop.dao.memory;

import com.codecool.shop.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDaoMemTest {
    List<Order> data = new ArrayList<>();

    @Test
    void getInstance() {
    }

    @Test
    void addOrderToMemReturnsTheProperSizeTest() {
        Order order = new Order();
        Order order1 = new Order();
        Order order2 = new Order();
        data.add(order);
        data.add(order1);
        data.add(order2);
        assertEquals(data.size(), 3);

    }

    @Test
    void addOrderToMemReturnsTheOrderId() {
        Order order = new Order();
        order.setId(data.size() + 1);
        assertEquals(order.getId(), 1);
    }

    @Test
    void addAll() {

    }

    @Test
    void find() {
    }

    @Test
    void remove() {
    }

    @Test
    void getAll() {
    }
}