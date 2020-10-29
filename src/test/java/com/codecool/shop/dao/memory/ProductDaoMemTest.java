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
        Assertions.assertEquals(1, product.getId());
    }

    @Test
    void addAll_addMultipleProducts_listSizeIncreases() {
        productDaoMem.addAll(product, product2);
        Assertions.assertEquals(2, productDaoMem.getAll().size());
    }

    @Test
    void addAll_addMultipleProducts_setsProperIdToProducts() {
        productDaoMem.addAll(product, product2);
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals(2, product2.getId());
    }

    @Test
    void find_withNotExistingId_returnsNull() {
        productDaoMem.add(product);
        Assertions.assertNull(productDaoMem.find(2));
    }

    @Test
    void find_withExistingId_returnsProduct() {
        productDaoMem.add(product);
        Assertions.assertEquals(product, productDaoMem.find(1));
    }

    @Test
    void find_withEmptyList_returnsNull() {
        Assertions.assertNull(productDaoMem.find(1));
    }

    @Test
    void remove_withNotExistingId_listSizeDoesNotChange() {
        productDaoMem.addAll(product, product2);
        productDaoMem.remove(4);
        Assertions.assertEquals(2, productDaoMem.getAll().size());
    }

    @Test
    void remove_withExistingId_listSizeDecreases() {
        productDaoMem.addAll(product, product2);
        productDaoMem.remove(2);
        Assertions.assertEquals(1, productDaoMem.getAll().size());
    }

    @Test
    void remove_removeAllItem_listSizeBecomesZero() {
        productDaoMem.addAll(product, product2);
        productDaoMem.remove(2);
        productDaoMem.remove(1);
        Assertions.assertEquals(0, productDaoMem.getAll().size());
    }

    @Test
    void getAll_withEmptyList_returnsEmptyList() {
        Assertions.assertEquals(0, productDaoMem.getAll().size());
    }

    @Test
    void getAll_withOneItem_returnsOneLongList() {
        productDaoMem.add(product);
        Assertions.assertEquals(1, productDaoMem.getAll().size());
    }

    @Test
    void getAll_withMultipleItems_returnsCorrectLongList() {
        productDaoMem.addAll(product, product2);
        Assertions.assertEquals(2, productDaoMem.getAll().size());
    }

    @Test
    void getByProductCategory_withNonExistingCategory_returnsEmptyList() {
        productDaoMem.addAll(product, product2);
        Assertions.assertEquals(0, productDaoMem.getBy(supplier2).size());
    }

    @Test
    void getByProductCategory_withExistingCategory_returnsCorrectLongList() {
        productDaoMem.addAll(product, product2);
        Assertions.assertEquals(2, productDaoMem.getBy(supplier).size());
    }

    @Test
    void getByProductCategoryAndSupplier_withNonExistingParameters_returnsEmptyList() {
        productDaoMem.addAll(product, product2);
        Assertions.assertEquals(0, productDaoMem.getBy(supplier2, productCategory2).size());
    }

    @Test
    void testGetBy1() {
    }
}