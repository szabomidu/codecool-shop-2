package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoMemTest {

    private static UserDao userDao;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void setup() {
        userDao = UserDaoMem.getInstance();
        user1 = mock(User.class);
        user1.setId(1);
        user2 = mock(User.class);
        user1.setId(2);
    }

    @Test
    @Order(1)
    void add() {
        when(userDao.add(user1)).thenReturn(1);
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    @Order(2)
    void addAll() {
        userDao.addAll(user1, user2);
        assertEquals(3, userDao.getAll().size());
    }

    @Test
    @Order(3)
    void find() {
        assertEquals(user1, userDao.find(1));
    }

    @Test
    @Order(4)
    void remove() {
        userDao.remove(2);
        assertEquals(2, userDao.getAll().size());
    }

    @Test
    void getAll() {

    }

    @Test
    void getBy() {
    }
}