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
    void add_addProduct_listSizeIncreases() {
        int originalSize = data.size();
        productDaoMem.add(product);
        when(data.add(product)).thenCallRealMethod();
        Assertions.assertEquals(originalSize + 1,  data.size());
    }

    @Test
    void add_addProduct_setsProperIdToProduct() {
        productDaoMem.add(product);
        doCallRealMethod().when(product).setId(data.size() + 1);
        Assertions.assertEquals(product.getId(),  data.size());
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