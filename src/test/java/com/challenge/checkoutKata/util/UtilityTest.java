package com.challenge.checkoutKata.util;

import com.challenge.checkoutKata.stock.StockItems;
import com.challenge.checkoutKata.wholesale.SpecialOffers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.challenge.checkoutKata.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {
    private static Utility utility;
    private StockItems stockItems;
    private SpecialOffers specialOffers;

    @BeforeEach()
    public void setUp() {
        //given
        utility = new Utility();
        stockItems = new StockItems();
        specialOffers =  new SpecialOffers();
    }

    @AfterEach()
    public void cleanUp() {

        utility = null;
        stockItems = null;
        specialOffers = null;
    }

    @Test
    public void readInputFromResource_withInvalidFile() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
            utility.readInputFromResource("TestFile")
        );
        //then
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void writeToFile_NoFileSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
            Utility.writeToFile(null, "A 50")
        );
        //then
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void writeToFile_NoInputSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                Utility.writeToFile("TestFile", null)
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void writeToFile_NoneExisting() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
            Utility.writeToFile("TestFile", "A 50")
        );
        //then
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void deleteALineFromFile_NoFileSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
            Utility.deleteALineFromFile(null, "A 50")
        );
        //then
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void deleteALineFromFile_NoInputSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
                Utility.deleteALineFromFile("TestFile", null)
        );
        //then
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void deleteALineFromFile_NoneExisting() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
            Utility.deleteALineFromFile("TestFile", "A 50")
        );
        //then
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void writeToFile_NoInputSpecified_withInvalidFileName() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
                Utility.writeToFile("", "\n")
        );
        //then
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    public void validateReadInput() throws ExceptionHandling {
        //when
        utility = new Utility();
        List<String> items = utility.readInputFromResource(ITEMS);
        //then
        assertNotNull(items);
    }

    @Test
    public void validateInitialise() throws ExceptionHandling {
        //when
        utility.initialise(stockItems, specialOffers);
        //then
        //no error
        assertNotNull(stockItems.getProducts());
        assertNotNull(specialOffers.getSpecialOffers());
    }

    @Test
    public void validateWriteAnd_Remove_WithValidInput() throws ExceptionHandling {

        //when
        List<String> items = utility.readInputFromResource(ITEMS);
        stockItems.loadStockItems(items);
        stockItems.addStockItem('L', new BigDecimal(99), TEST_ITEMS_FILE);

        //then
        //no error
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());
        stockItems.removeStockItem('L', TEST_ITEMS_FILE);
        assertEquals(items.size(), stockItems.getProducts().size());    //the size stays the same
    }


}