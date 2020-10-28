package com.codecool.shop.dao.memory;

import com.codecool.shop.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


class ProductDaoMemTest {
    private List<Product> data;
    private ProductDaoMem productDaoMem;
    @Mock ProductDaoMem mockProductDaoMem = Mockito.mock(ProductDaoMem.class);
    @Mock Product product = Mockito.mock(Product.class, Mockito.RETURNS_DEEP_STUBS);

    @BeforeEach
    void setUp() {
        data = new ArrayList<>();
        productDaoMem = ProductDaoMem.getInstance();

    }

    @Test
    void getInstance() {
    }

    @Test
    void add() {
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

    @Test
    void getBy() {
    }

    @Test
    void testGetBy() {
    }

    @Test
    void testGetBy1() {
    }
}