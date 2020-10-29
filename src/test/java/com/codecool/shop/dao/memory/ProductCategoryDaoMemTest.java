package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {

    private static ProductCategoryDao productCategoryDao = ProductCategoryDaoMem.getInstance();
    private static ProductCategory categoryOne = new ProductCategory("One", "First", "1st");
    private static ProductCategory categoryTwo = new ProductCategory("Two", "Second", "2nd");
    private static ProductCategory categoryThree = new ProductCategory("Three", "Third", "3rd");

    @BeforeEach
    void setup() {
        productCategoryDao.add(categoryOne);
    }

    @AfterEach
    void clear() {
        productCategoryDao.clearData();
    }

    @Test
    void add_increasesSizeOfStorage() {
        assertEquals(1, productCategoryDao.getAll().size());
    }

    @Test
    void add_setsIdAccordingToSize() {
        assertEquals(1, categoryOne.getId());
    }

    @Test
    void addAll_addsAllGivenLineItemsToList() {
        productCategoryDao.addAll(categoryTwo, categoryThree);
        assertEquals(3, productCategoryDao.getAll().size());
    }

    @Test
    void addAll_setsAllIdsProperly() {
        productCategoryDao.addAll(categoryTwo, categoryThree);
        assertTrue(categoryOne.getId() == 1
                && categoryTwo.getId() == 2
                && categoryThree.getId() == 3);
    }

    @Test
    void find_IfIdIsPresent_ReturnsLineItemWithGivenId() {
        assertEquals(categoryOne, productCategoryDao.find(1));
    }

    @Test
    void find_WhenCategoryWithIdIsNotPresent_ReturnsNull() {
        assertNull(productCategoryDao.find(4));
    }

    @Test
    void find_DoesNotEffectSizeOfList() {
        productCategoryDao.find(1);
        assertEquals(1, productCategoryDao.getAll().size());
    }

    @Test
    void findByName_ReturnsCategoryWithTheGivenName() {
        productCategoryDao.addAll(categoryTwo, categoryThree);
        ProductCategory category = productCategoryDao.findByName("Two");
        assertEquals(categoryTwo, category);
    }

    @Test
    void findByName_WhenCategoryWithNameIsNotPresent_ReturnsNull() {
        assertNull(productCategoryDao.findByName("Empty"));
    }

    @Test
    void remove_WhenIdIsPresent_DecreasesSizeOfList() {
        productCategoryDao.remove(1);
        assertEquals(0, productCategoryDao.getAll().size());
    }

    @Test
    void remove_WhenIdIsNotPresent_DoesNotDecreaseSizeOfList() {
        productCategoryDao.remove(4);
        assertEquals(1, productCategoryDao.getAll().size());
    }

    @Test
    void clearData_RemovesAllItems() {
        productCategoryDao.addAll(categoryTwo, categoryThree);
        productCategoryDao.clearData();
        assertEquals(0, productCategoryDao.getAll().size());
    }
}