package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.model.Order;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoMemTest {

    private static UserDao userDao;
    private static Order order1;
    private static User user1;
    private static User user2;
    private static User user3;

    @BeforeAll
    static void setup() {
        userDao = UserDaoMem.getInstance();
        order1 = mock(Order.class);
        user1 = mock(User.class);
        user2 = mock(User.class);
        user3 = mock(User.class);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void add() {
        userDao.add(user1);
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void addAll() {
        userDao.addAll(user2, user3);
        assertEquals(3, userDao.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void find() {
        User expectedUser = userDao.find(0);
        assertEquals(user1, expectedUser);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void remove() {
        userDao.remove(0);
        assertEquals(2, userDao.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getAll() {
        assertEquals(2, userDao.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void getBy() {
    }
}