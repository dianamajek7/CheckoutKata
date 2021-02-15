package wholesale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shopping.Basket;
import shopping.Checkout;
import stock.StockItems;
import util.Utility;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;

class SpecialOffersTest {
    StockItems stockItems;
    SpecialOffers specialOffers;
    Basket basket;
    Checkout checkout;
    static Utility utility;

    @BeforeEach()
    public void setUp(){
        //given
        utility = new Utility();
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
        utility.initialise(stockItems, specialOffers);
        basket = new Basket(stockItems);
        checkout = new Checkout(specialOffers);
    }

    @AfterEach
    public void cleanUp() {
        stockItems = null;
        specialOffers = null;
    }


    @Test
    public void validateWholeSale_With_ANewPricingRule() {
        //when
        int oldSize = specialOffers.getSpecialOffers().size();
        String msg = specialOffers.addSpecialOffer('E', 4, new BigDecimal(95), stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNull(msg); //no error message with input
        assertEquals(oldSize+ 1, specialOffers.getSpecialOffers().size());  //validate the increment in size of list

        msg = specialOffers.removeSpecialOffer('E', 4, TEST_SPECIALPRICE_FILE); //removed input in file, to be reused by other test cases
        assertNull(msg);

    }
    //
    @Test
    public void validateWholeSale_addSpecialPrice_With_An_ExistingPricingRule() {
        //when
        String msg = specialOffers.addSpecialOffer('B', 2, new BigDecimal(45), stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNotNull(msg);
        assertEquals(utility.readInputFromResource(SPECIALPRICES).size(), specialOffers.getSpecialOffers().size());
        assertEquals(PRICINGRULE_EXIST, msg);
    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_A_NonExisting_Item() {
        //when
        String msg = specialOffers.addSpecialOffer('F', 2, new BigDecimal(45), stockItems.getProducts(), TEST_SPECIALPRICE_FILE);

        //then
        assertNotNull(msg);
        assertEquals(utility.readInputFromResource(SPECIALPRICES).size(), specialOffers.getSpecialOffers().size());
        assertEquals(ITEM_NOTFOND + ": F", msg);
    }

    @Test
    public void validateWholeSale_Remove_PricingRule() {

        //when
        String msg = specialOffers.addSpecialOffer('A', 7, new BigDecimal(50), stockItems.getProducts(), TEST_SPECIALPRICE_FILE);
        int oldSize = specialOffers.getSpecialOffers().size();
        //then
        assertNull(msg);

        msg = specialOffers.removeSpecialOffer('A', 7, TEST_SPECIALPRICE_FILE);
        assertNull(msg);
        assertEquals(oldSize - 1, specialOffers.getSpecialOffers().size());    //the size stays the same

    }

    @Test
    public void validateInventory_removeNonExisting_PricingRule() {

        //when
        String msg = specialOffers.removeSpecialOffer('Z', 3, TEST_SPECIALPRICE_FILE);
        int oldSize = specialOffers.getSpecialOffers().size();
        //then
        assertEquals(PRICINGRULE_NOTPRESENT, msg);
        assertEquals(oldSize, specialOffers.getSpecialOffers().size());

    }

}