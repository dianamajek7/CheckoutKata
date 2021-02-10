import Stock.StockItems;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.SkuUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;

public class StockItemsTest {
    SkuUtil skuUtil;
    StockItems stockItems;
    List<String> items;

    @BeforeEach()
    public void setUp(){
        //given
        skuUtil = new SkuUtil();
        items = skuUtil.readInputFromResource(ITEMS);
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
        String msg = stockItems.addStockItem('F', 50f, TEST_ITEM_FILE);

        //then
        assertNull(msg); //no error message with input
        assertEquals(skuUtil.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());  //validate the increment in size of list
        msg = stockItems.removeStockItem('F', TEST_ITEM_FILE);
        assertNull(msg);
        //removeLastLineFromFile(TEST_ITEM_FILE);    //removed input in file, to be reused by other test cases

    }

    @Test
    public void validateInventory_addItem_ThatExists() {
        //when
        stockItems.loadStockItems(items);
        String msg = stockItems.addStockItem('B', 30f, TEST_ITEM_FILE);

        //then
        assertNotNull(msg);
        assertEquals(skuUtil.readInputFromResource(ITEMS).size(), stockItems.getProducts().size());
        assertEquals(ITEM_EXIST, msg);
    }

    @Test
    public void validateInventory_Remove_AnItem() {

        //when
        stockItems.loadStockItems(items);
        String msg = stockItems.addStockItem('L', 99f, TEST_ITEM_FILE);

        //then
        assertEquals(skuUtil.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());
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
