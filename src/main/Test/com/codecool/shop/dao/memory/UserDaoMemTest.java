package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.model.Order;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoMemTest {

    private static UserDao userDao;
    private static final Order order = new Order();
    private static final User user1 = new User("user1");
    private static final User user2 = new User("user2");
    private static final User user3 = new User("user3");


    @BeforeEach
    void setup() {
        userDao = UserDaoMem.getInstance();
        userDao.add(user1);
    }

    @AfterEach
    void clearUsers() {
        userDao.clearData();
    }

    @Test
    void add() {
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    void addAll() {
        userDao.addAll(user2, user3);
        assertEquals(3, userDao.getAll().size());
    }

    @Test
    void find() {
        User expectedUser = userDao.find(1);
        assertEquals(user1, expectedUser);
    }

    @Test
    void remove() {
        userDao.remove(1);
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    void getAll() {
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    void getBy() {
        user1.addOrder(order);
        assertEquals(user1, userDao.getBy(order));
    }
}