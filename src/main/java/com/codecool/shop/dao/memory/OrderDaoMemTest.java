package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderDaoMemTest {
    private static OrderDao orderDao;
    private static final Order order1 = new Order();
    private static final Order order2 = new Order();
    private static final Order order3 = new Order();

    @BeforeEach
    void setup() {
        orderDao = OrderDaoMem.getInstance();
        orderDao.add(order1);
    }

    @AfterEach
    void clearOrders() {
        orderDao.clearData();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void add() {
        orderDao.add(order1);
        assertEquals(2, orderDao.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void addMethodSetsTheProperIdForOrder() {
        int expectedId = 1;
        assertEquals(expectedId, orderDao.find(1).getId());
    }


    @Test
    @org.junit.jupiter.api.Order(2)
    void addAll() {
        orderDao.addAll(order2, order3);
        assertEquals(3, orderDao.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void find() {
        Order expectedOrder = orderDao.find(1);
        assertEquals(order1, expectedOrder);
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void findOrderThatDoesntExistsReturnNull() {
        Order expectedOrder = orderDao.find(2);
        assertNull(expectedOrder);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void remove() {
        orderDao.remove(1);
        assertEquals(0, orderDao.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getAll() {
        assertEquals(1, orderDao.getAll().size());
    }

}