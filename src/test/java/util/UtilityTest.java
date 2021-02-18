package util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stock.StockItems;
import wholesale.SpecialOffers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;

class UtilityTest {
    static Utility utility;
    StockItems stockItems;
    SpecialOffers specialOffers;

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
    void readInputFromResource_withInvalidFile() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            utility.readInputFromResource("TestFile")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void writeToFile_NoFileSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.writeToFile(null, "A 50")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void writeToFile_NoInputSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                Utility.writeToFile("TestFile", null)
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void writeToFile_NoneExisting() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.writeToFile("TestFile", "A 50")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void deleteALineFromFile_NoFileSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.deleteALineFromFile(null, "A 50")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void deleteALineFromFile_NoInputSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                Utility.deleteALineFromFile("TestFile", null)
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void deleteALineFromFile_NoneExisting() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.deleteALineFromFile("TestFile", "A 50")
        );
         assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

//    @Test
//    void deleteALineFromFile_ExistingFile_WithInvalidInputs() {
//        Exception exception = assertThrows(ExceptionHandling.class, () ->
//                Utility.deleteALineFromFile(TEST_ITEM_FILE, "\\t")
//        );
//        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
//    }

    @Test
    void writeToFile_NoInputSpecified_withInvalidFileName() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                Utility.writeToFile("", "\n")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void validateReadInput() throws ExceptionHandling {
        //given
        utility = new Utility();
        List<String> items = utility.readInputFromResource(ITEMS);
        assertNotNull(items);
    }

    @Test
    void validateInitialise() throws ExceptionHandling {
        //given
        utility.initialise(stockItems, specialOffers);
        assertNotNull(stockItems.getProducts());
        assertNotNull(specialOffers.getSpecialOffers());


    }

    @Test
    public void validateWriteAnd_Remove_WithValidInput() throws ExceptionHandling {

        //when
        List<String> items = utility.readInputFromResource(ITEMS);
        stockItems.loadStockItems(items);
        stockItems.addStockItem('L', new BigDecimal(99), TEST_ITEM_FILE);

        //then
        //no error
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());
        stockItems.removeStockItem('L', TEST_ITEM_FILE);
        assertEquals(items.size(), stockItems.getProducts().size());    //the size stays the same
    }


}