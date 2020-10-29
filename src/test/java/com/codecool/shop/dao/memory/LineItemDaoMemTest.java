package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LineItemDaoMemTest {

    private static LineItemDao lineItemDao = LineItemDaoMem.getInstance();
    private static Product product = mock(Product.class);
    private static LineItem lineItemOne = new LineItem(product, 1);
    private static LineItem lineItemTwo = new LineItem(product, 2);
    private static LineItem lineItemThree = new LineItem(product, 3);

    @BeforeEach
    void setup() {
        lineItemDao.add(lineItemOne);
    }

    @AfterEach
    void clear() {
        lineItemDao.clearData();
    }

    @Test
    void add_increasesSizeOfStorage() {
        assertEquals(1, lineItemDao.getAll().size());
    }

    @Test
    void add_setsIdAccordingToSize() {
        assertEquals(1, lineItemOne.getId());
    }

    @Test
    void addAll_addsAllGivenLineItemsToList() {
        lineItemDao.addAll(lineItemTwo, lineItemThree);
        assertEquals(3, lineItemDao.getAll().size());
    }

    @Test
    void addAll_setsAllIdsProperly() {
        lineItemDao.addAll(lineItemTwo, lineItemThree);
        assertTrue(lineItemOne.getId() == 1
                        && lineItemTwo.getId() == 2
                        && lineItemThree.getId() == 3);
    }

    @Test
    void find_ReturnsLineItemWithGivenId() {
        assertEquals(lineItemOne, lineItemDao.find(1));
    }

    @Test
    void find_DoesNotEffectSizeOfList() {
        LineItem lineItem = lineItemDao.find(1);
        assertEquals(1, lineItemDao.getAll().size());
    }

    @Test
    void remove_DecreasesSizeOfList() {
        lineItemDao.remove(1);
        assertEquals(0, lineItemDao.getAll().size());
    }

    @Test
    void getAll_ReturnsCollectionOfAllLineItems() {
        lineItemDao.addAll(lineItemTwo, lineItemThree);
        List<LineItem> lineItems = lineItemDao.getAll();
        assertTrue(lineItems.get(0).equals(lineItemOne)
                            && lineItems.get(1).equals(lineItemTwo)
                            && lineItems.get(2).equals(lineItemThree));
    }
}