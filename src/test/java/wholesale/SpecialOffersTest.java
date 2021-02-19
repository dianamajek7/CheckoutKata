package wholesale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stock.StockItems;
import util.ExceptionHandling;
import util.Utility;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.Constants.*;

class SpecialOffersTest {
    private StockItems stockItems;
    private SpecialOffers specialOffers;
    private List<String> items;
    private List<String> specialPricing;
    private static Utility utility;

    @BeforeEach()
    public void setUp() {
        //given
        utility = new Utility();
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();


    }

    @AfterEach
    public void cleanUp() {
        stockItems = null;
        specialOffers = null;
        items = null;
        specialPricing = null;
    }

    @Test
    public void validate_loadSpecialPricing() throws ExceptionHandling {
        //given
        items = utility.readInputFromResource(ITEMS);
        specialPricing = utility.readInputFromResource(SPECIALPRICES);
        stockItems.loadStockItems(items);
        //when
        specialOffers.loadSpecialOffers(specialPricing, stockItems.getProducts());

        //then
        assertEquals(specialPricing.size(), specialOffers.getSpecialOffers().size());
    }

    @Test
    public void validate_loadEmptySpecialPricing() throws ExceptionHandling {
        //when
        items = utility.readInputFromResource(ITEMS);
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(null, stockItems.getProducts());

        //then
        assertEquals(0, specialOffers.getSpecialOffers().size());
    }

    @Test
    public void validate_addSpecialPricing() throws ExceptionHandling {
        //when
        items = utility.readInputFromResource(ITEMS);
        stockItems.loadStockItems(items);
        specialOffers.addSpecialPrice(null);

        //then
        assertEquals(0, specialOffers.getSpecialOffers().size());
    }

    @Test
    public void validateWholeSale_With_ANewPricingRule() throws ExceptionHandling {
        //given
        items = utility.readInputFromResource(ITEMS);
        specialPricing = utility.readInputFromResource(SPECIALPRICES);
        utility.initialise(stockItems, specialOffers);

        //when
        int oldSize = specialOffers.getSpecialOffers().size();
        specialOffers.addSpecialOffer('E', 4, new BigDecimal(95), stockItems.getProducts(), TEST_SPECIALPRICES_FILE);

        //then
        //no error
        assertEquals(oldSize+ 1, specialOffers.getSpecialOffers().size());  //validate the increment in size of list
        specialOffers.removeSpecialOffer('E', 4, TEST_SPECIALPRICES_FILE); //removed input in file, to be reused by other test cases
    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_An_ExistingPricingRule() throws ExceptionHandling {
        Exception exception = assertThrows(ExceptionHandling.class, () -> {
            //given
            items = utility.readInputFromResource(ITEMS);
            specialPricing = utility.readInputFromResource(SPECIALPRICES);
            utility.initialise(stockItems, specialOffers);

            //when
            specialOffers.addSpecialOffer('B', 2, new BigDecimal(45), stockItems.getProducts(), TEST_SPECIALPRICES_FILE);
        });
        //then
        assertEquals(utility.readInputFromResource(SPECIALPRICES).size(), specialOffers.getSpecialOffers().size());
        assertEquals(PRICINGRULE_EXIST, exception.getMessage());
    }

    @Test
    public void validateWholeSale_addSpecialPrice_With_A_NonExisting_Item() throws ExceptionHandling {

        Exception exception = assertThrows(ExceptionHandling.class, () -> {
            //given
            items = utility.readInputFromResource(ITEMS);
            specialPricing = utility.readInputFromResource(SPECIALPRICES);
            utility.initialise(stockItems, specialOffers);

            //when
            specialOffers.addSpecialOffer('F', 2, new BigDecimal(45), stockItems.getProducts(), TEST_SPECIALPRICES_FILE);
        });
        //then
        assertEquals(utility.readInputFromResource(SPECIALPRICES).size(), specialOffers.getSpecialOffers().size());
        assertEquals(ITEM_NOTFOND + ": F", exception.getMessage());
    }

    @Test
    public void validateWholeSale_Remove_PricingRule() throws ExceptionHandling {
        //given
        items = utility.readInputFromResource(ITEMS);
        specialPricing = utility.readInputFromResource(SPECIALPRICES);
        utility.initialise(stockItems, specialOffers);

        //when
        specialOffers.addSpecialOffer('A', 7, new BigDecimal(50), stockItems.getProducts(), TEST_SPECIALPRICES_FILE);
        int oldSize = specialOffers.getSpecialOffers().size();
        //then

        specialOffers.removeSpecialOffer('A', 7, TEST_SPECIALPRICES_FILE);
        assertEquals(oldSize - 1, specialOffers.getSpecialOffers().size());    //the size stays the same

    }

    @Test
    public void validateInventory_removeNonExisting_PricingRule() {

        Exception exception = assertThrows(ExceptionHandling.class, () -> {
            //given
            items = utility.readInputFromResource(ITEMS);
            specialPricing = utility.readInputFromResource(SPECIALPRICES);
            utility.initialise(stockItems, specialOffers);

            //when
            specialOffers.removeSpecialOffer('Z', 3, TEST_SPECIALPRICES_FILE);
        });
        int oldSize = specialOffers.getSpecialOffers().size();
        //then
        assertEquals(PRICINGRULE_NOTPRESENT, exception.getMessage());
        assertEquals(oldSize, specialOffers.getSpecialOffers().size());

    }

}