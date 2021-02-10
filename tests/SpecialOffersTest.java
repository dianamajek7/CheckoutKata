import Stock.StockItems;
import Wholesale.SpecialOffers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.SkuUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;

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

    @AfterEach
    public void cleanUp() {
        items = null;
        stockItems = null;
        specialPrices = null;
        specialOffers = null;
    }

    @Test
    public void validateWholeSale_LoadSpecialOffers() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());

        //then
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());
    }

    @Test
    public void validateWholeSale_With_ANewPricingRule() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());    //checking the current size
        String msg = specialOffers.addSpecialOffer('E', 4, 95f, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNull(msg); //no error message with input
        assertEquals(specialPrices.size() + 1, specialOffers.getSpecialOffers().size());  //validate the increment in size of list

        msg = specialOffers.removeSpecialOffer('E', 4, TEST_SPECIALPRICE_FILE); //removed input in file, to be reused by other test cases
        assertNull(msg);

    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_An_ExistingPricingRule() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());
        String msg = specialOffers.addSpecialOffer('B', 2, 45f, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNotNull(msg);
        assertEquals(skuUtil.readInputFromResource(SPECIALPRICES).size(), specialOffers.getSpecialOffers().size());
        assertEquals(PRICINGRULE_EXIST, msg);
    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_A_NonExisting_Item() {
        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());
        String msg = specialOffers.addSpecialOffer('F', 2, 45f, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNotNull(msg);
        assertEquals(skuUtil.readInputFromResource(SPECIALPRICES).size(), specialOffers.getSpecialOffers().size());
        assertEquals(ITEM_NOTFOND + ": F", msg);
    }

    @Test
    public void validateWholeSale_Remove_PricingRule() {

        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
        String msg = specialOffers.addSpecialOffer('A', 7, 50f, stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertEquals(specialPrices.size() + 1, specialOffers.getSpecialOffers().size());
        assertNull(msg);

        msg = specialOffers.removeSpecialOffer('A', 7, TEST_SPECIALPRICE_FILE);
        assertNull(msg);
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());    //the size stays the same

    }

    @Test
    public void validateInventory_removeNonExisting_PricingRule() {

        //when
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());

        //then
        String msg = specialOffers.removeSpecialOffer('Z', 3, TEST_SPECIALPRICE_FILE);
        assertEquals(PRICINGRULE_NOTPRESENT, msg);
        assertEquals(specialPrices.size(), specialOffers.getSpecialOffers().size());

    }

}
