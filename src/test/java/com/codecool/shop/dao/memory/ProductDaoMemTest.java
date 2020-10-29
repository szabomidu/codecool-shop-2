package com.codecool.shop.dao.memory;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ProductDaoMemTest {
    private ProductDaoMem productDaoMem;
    Supplier supplier;
    Supplier supplier2;
    ProductCategory productCategory;
    ProductCategory productCategory2;
    Product product;
    Product product2;

    @BeforeEach
    void setUp() {
        supplier = new Supplier("Test supplier", "test description");
        supplier2 = new Supplier("Negative Test supplier", "negative test description");
        productCategory = new ProductCategory("Test category", "test department", "test description");
        productCategory2 = new ProductCategory("Negative Test category", " Negative test department", "Negative test description");
        product = new Product("Test product", (float) 12.5, "USD", "test description", productCategory, supplier);
        product2 = new Product("Test product2", (float) 12.5, "USD", "test description2", productCategory, supplier);
        productDaoMem = ProductDaoMem.getInstance();
    }

    @AfterEach
    void tearDown() {
        productDaoMem.clearData();
    }

    @Test
    void add_addProduct_listSizeIncreases() {
       productDaoMem.add(product);
       Assertions.assertEquals(1, productDaoMem.getAll().size());
    }

    @Test
    void add_addProduct_setsProperIdToProduct() {
        productDaoMem.add(product);
        doCallRealMethod().when(product).setId(data.size() + 1);
        Assertions.assertEquals(product.getId(),  data.size());
    }


    @Test
    void addAll() {
//        int numberOfCalls = 2;
//        mockProductDaoMem.addAll(product, product);
//        verify(mockProductDaoMem, times(numberOfCalls)).add(product);
//        doCallRealMethod().when(mockProductDaoMem).addAll(product, product);
    }

    @Test
    void find() {
        productDaoMem.add(product);
        doCallRealMethod().when(product).setId(data.size() + 1);
        when(data.add(product)).thenCallRealMethod();
        Assertions.assertEquals(Product.class, productDaoMem.find(data.size() + 1).getClass());
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