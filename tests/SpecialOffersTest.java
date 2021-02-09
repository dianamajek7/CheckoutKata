import Stock.StockItems;
import Wholesale.SpecialOffers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.SkuUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;
import static util.SkuUtil.removeLastLineFromFile;

public class SpecialOffersTest {
    SkuUtil skuUtil;
    StockItems stockItems;
    SpecialOffers specialOffers;
    List<String> items;
    List<String> specialPrices;

    @BeforeEach()
    public void setUp(){
        //given
         skuUtil = new SkuUtil();
         items = skuUtil.readInputFromResource(ITEMS);
         stockItems = new StockItems();
         specialPrices = skuUtil.readInputFromResource(SPECIALPRICES);
         specialOffers = new SpecialOffers();
    }

//    @Test
//    public void validateInventory_LoadStocks() {
//        //given
//        SkuUtil skuUtil = new SkuUtil();
//        List<String> items = skuUtil.readInputFromResource("Items");
//        StockItems stockItems = new StockItems();
//        //when
//        stockItems.loadStockItems(items);
//
//        //then
//        assertNotNull(product.size());
//        assertEquals(5, product.size());
//    }
//
    @Test
    public void validateWholeSale_LoadSpecialOffers() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());

        //then
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());
    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_ANewPricingRule() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());    //checking the current size
        String msg = specialOffers.addSpecialOffers('E', 4, 95, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNull(msg); //no error message with input
        assertEquals(skuUtil.readInputFromResource(SPECIALPRICES).size(), specialPrices.size());  //validate the increment in size of list
        removeLastLineFromFile(TEST_SPECIALPRICE_FILE);    //removed input in file, to be reused by other test cases

    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_An_ExistingPricingRule() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());
        String msg = specialOffers.addSpecialOffers('B', 2, 45, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNotNull(msg);
        assertEquals(skuUtil.readInputFromResource(SPECIALPRICES).size(), specialPrices.size());
        assertEquals("Special Pricing already exist", msg);
    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_A_NonExisting_Item() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());
        String msg = specialOffers.addSpecialOffers('F', 2, 45, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNotNull(msg);
        assertEquals(skuUtil.readInputFromResource(SPECIALPRICES).size(), specialPrices.size());
        assertEquals("Item Specified not Found for F", msg);
    }

    @Test
    public void validateWholeSale_AddAndRemove_NewPricingRue() {

        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        String msg = specialOffers.addSpecialOffers('A', 7, 50, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertEquals(specialPrices.size() + 1, specialOffers.getSpecialOffers().size());
        assertNull(msg);

        msg = specialOffers.removeSpecialOffer('A', 7, TEST_SPECIALPRICE_FILE);
        assertNull(msg);
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());    //the size stays the same

    }

    @Test
    public void validateInventory_removeOldPricingRule_whilstAddingNewRule() {

        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        String msg = specialOffers.addSpecialOffers('A', 7, 50, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNull(msg);
        assertEquals(specialPrices.size() + 1, specialOffers.getSpecialOffers().size());

        msg = specialOffers.removeSpecialOffer('B', 3, TEST_SPECIALPRICE_FILE);
        assertNull(msg);
        assertEquals(skuUtil.readInputFromResource(SPECIALPRICES).size(), specialOffers.getSpecialOffers().size()); //latest pricing rule file size

        // add back the original price rule before deletion
        msg = specialOffers.removeSpecialOffer('A', 7, TEST_SPECIALPRICE_FILE);
        assertNull(msg);
        msg = specialOffers.addSpecialOffers('B', 3, 75, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);
        assertNull(msg);
    }

    @Test
    public void validateInventory_removeStock_WithANonExistingItem() {

        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());

        //then
        String msg = specialOffers.removeSpecialOffer('Z', 3, TEST_SPECIALPRICE_FILE);
        assertEquals(PRICINGRULE_NOTPRESENT, msg);
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());

    }

}
