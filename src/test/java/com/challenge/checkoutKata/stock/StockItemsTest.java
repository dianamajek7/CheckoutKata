package com.challenge.checkoutKata.stock;

import com.challenge.checkoutKata.util.ExceptionHandling;
import com.challenge.checkoutKata.util.Utility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.challenge.checkoutKata.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockItemsTest {
    private StockItems stockItems;
    private List<String> items;
    private static Utility utility;

    @BeforeEach()
    public void setUp() throws ExceptionHandling {
        //given
        utility = new Utility();
        items = utility.readInputFromResource(ITEMS);
        stockItems = new StockItems();
    }

    @AfterEach
    public void cleanUp() {
        items = null;
        stockItems = null;
    }

    @Test
    public void validateInventory_LoadStocks() {
        //when
        stockItems.loadStockItems(items);

        //then
        assertEquals(items.size(), stockItems.getProducts().size());
    }

    @Test
    public void validateInventory_LoadEmptyStock() {
        //when
        stockItems.loadStockItems(null);

        //then
        assertEquals(0, stockItems.getProducts().size());
    }

    @Test
    public void validateAddProduct() {
        stockItems.addProduct(null);

        assertEquals(0, stockItems.getProducts().size());
    }

    @Test
    public void validateInventory_With_ANewProduct() throws ExceptionHandling {
        //when
        stockItems.loadStockItems(items);
        assertEquals(items.size(), stockItems.getProducts().size());    //checking the current size
        stockItems.addStockItem('F', new BigDecimal(50), TEST_ITEMS_FILE);

        //then
        //no error
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());  //validate the increment in size of list
        stockItems.removeStockItem('F', TEST_ITEMS_FILE);
    }

    @Test
    public void validateInventory_addItem_ThatExists() throws ExceptionHandling {

        Exception exception = assertThrows(ExceptionHandling.class, () -> {
            //when
            stockItems.loadStockItems(items);
            stockItems.addStockItem('B', new BigDecimal(30), TEST_ITEMS_FILE);
        });
        //then
        assertEquals(utility.readInputFromResource(ITEMS).size(), stockItems.getProducts().size());
        assertEquals(ITEM_EXIST, exception.getMessage());

    }

    @Test
    public void validateInventory_Remove_AnItem() throws ExceptionHandling {

        //when
        stockItems.loadStockItems(items);
        stockItems.addStockItem('L', new BigDecimal(99), TEST_ITEMS_FILE);

        //then
        //no error
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());
        stockItems.removeStockItem('L', TEST_ITEMS_FILE);
        assertEquals(items.size(), stockItems.getProducts().size());    //the size stays the same
    }

    @Test
    public void validateInventory_Remove_ANonExisting_Item() {
        Exception exception = assertThrows(ExceptionHandling.class, () -> {
            //when
            stockItems.loadStockItems(items);
            stockItems.removeStockItem('L', TEST_ITEMS_FILE);
        });
        //then
        assertEquals(ITEM_NOTFOND, exception.getMessage());
        assertEquals(items.size(), stockItems.getProducts().size());    //the size stays the same
    }

}