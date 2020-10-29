package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {

    private static SupplierDao supplierDaoMem;
    private static final Supplier supplier1 = new Supplier("supplier1", "description1");
    private static final Supplier supplier2 = new Supplier("supplier2", "description2");
    private static final Supplier supplier3 = new Supplier("supplier3", "description3");


    @BeforeEach
    void setup() {
        supplierDaoMem = SupplierDaoMem.getInstance();
        supplierDaoMem.add(supplier1);
    }

    @AfterEach
    void clearSuppliers() {
        supplierDaoMem.clearData();
    }

    @Test
    void add() {
        assertEquals(1, supplierDaoMem.getAll().size());
    }

    @Test
    void addAll() {
        supplierDaoMem.addAll(supplier2, supplier3);
        assertEquals(3, supplierDaoMem.getAll().size());
    }

    @Test
    void find() {
        assertEquals(supplier1, supplierDaoMem.find(1));
    }

    @Test
    void findByName() {
        assertEquals(supplier1, supplierDaoMem.findByName("supplier1"));
    }

    @Test
    void remove() {
        supplierDaoMem.remove(1);
        assertEquals(0, supplierDaoMem.getAll().size());
    }

    @Test
    void getAll() {
        supplierDaoMem.addAll(supplier2, supplier3);
        assertEquals(3, supplierDaoMem.getAll().size());
    }
}