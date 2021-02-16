package stock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Utility;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;

class StockItemsTest {
    StockItems stockItems;
    List<String> items;
    static Utility utility;

    @BeforeEach()
    public void setUp(){
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
    public void validateInventory_With_ANewProduct() {
        //when
        stockItems.loadStockItems(items);
        assertEquals(items.size(), stockItems.getProducts().size());    //checking the current size
        String msg = stockItems.addStockItem('F', new BigDecimal(50), TEST_ITEM_FILE);

        //then
        assertNull(msg); //no error message with input
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());  //validate the increment in size of list
        msg = stockItems.removeStockItem('F', TEST_ITEM_FILE);
        assertNull(msg);

    }

    @Test
    public void validateInventory_addItem_ThatExists() {
        //when
        stockItems.loadStockItems(items);
        String msg = stockItems.addStockItem('B', new BigDecimal(30), TEST_ITEM_FILE);

        //then
        assertNotNull(msg);
        assertEquals(utility.readInputFromResource(ITEMS).size(), stockItems.getProducts().size());
        assertEquals(ITEM_EXIST, msg);
    }

    @Test
    public void validateInventory_Remove_AnItem() {

        //when
        stockItems.loadStockItems(items);
        String msg = stockItems.addStockItem('L', new BigDecimal(99), TEST_ITEM_FILE);

        //then
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());
        assertNull(msg);

        msg = stockItems.removeStockItem('L', TEST_ITEM_FILE);
        assertNull(msg);
        assertEquals(items.size(), stockItems.getProducts().size());    //the size stays the same

    }

    @Test
    public void validateInventory_Remove_ANonExisting_Item() {

        //when
        stockItems.loadStockItems(items);
        String msg = stockItems.removeStockItem('L', TEST_ITEM_FILE);
        assertEquals(ITEM_NOTFOND, msg);
        assertEquals(items.size(), stockItems.getProducts().size());    //the size stays the same

    }

}