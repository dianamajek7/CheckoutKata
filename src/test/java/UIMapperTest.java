import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shopping.Basket;
import shopping.Checkout;
import stock.StockItems;
import util.ExceptionHandling;
import util.Utility;
import wholesale.SpecialOffers;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Constants.*;

class UIMapperTest {
    private Basket basket;
    private Checkout checkout;
    private StockItems stockItems;
    private SpecialOffers specialOffers;
    private static Utility utility;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach()
    public void setUp() throws ExceptionHandling {
        //given
        utility = new Utility();
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
        utility.initialise(stockItems, specialOffers);
        basket = new Basket(stockItems);
        checkout = new Checkout(specialOffers);

        //initialise the test output stream
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

    }

    @AfterEach
    public void cleanUp() {
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();

        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void setInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        String[] inputArray = testOut.toString().split("\n");
        int inputLength = inputArray.length;
        return inputArray[inputLength - 1];
    }

    @Test
    public void shopping_WithInvalidFormat() {
        //given
        String testInput = "A B C D";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void shopping_WithCharacterInput() {
        //given
        String testInput = "/";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void shopping_WithCharactersAndNumbersInput() {
        //given
        String testInput = "AA89";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void shopping_WithNumbersInput() {
        //given
        String testInput = "4334";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void shopping_WithItem_NotFound() {
        //given
        String testInput = "ZZ";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ITEM_NOTFOND + ": Z", getOutput());
    }

    @Test
    public void shopping_WithValidItems() {
        //given
        String testInput = "ABB";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then
        assertEquals("Total price: 95", getOutput());
    }

    @Test
    public void OutputFileContent_EmptyFile() throws IOException {
        //given
        FileWriter fileWriter = new FileWriter("src/test/resources/temp.txt");
        //when
        fileWriter.append("\n");
        UIMapper.OutputFileContent("temp.txt");

        //then
        assertEquals(NONE_AVAILABLE, getOutput());
        (new File("src/test/resources/temp.txt")).delete();

    }

    @Test
    public void modifyStocks_WithInvalidOptionChar() {
        //given
        String testInput = "A";
        setInput(testInput);
        //when
        UIMapper.modifyStocks(stockItems);

        //then
        assertEquals(NUMERIC_ERROR, getOutput());
    }

    @Test
    public void modifyStocks_AddItem_WithValidInput() {
        //given
        String testInput = "1\nA 4\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ITEM_EXIST, getOutput());
    }

    @Test
    public void modifyStocks_WithAddItemZeroPrice() {
        //given
        String testInput = "1\nA 0\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(PRICE_ZERO, getOutput());
    }

    @Test
    public void modifyStocks_AddItemName() {
        //given
        String testInput = "1\nA3\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, getOutput());
    }

    @Test
    public void modifyStocks_WithNoneExistingOption() {
        //given
        String testInput = "3\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(INVALID_OPTION, getOutput());
    }

    @Test
    public void modifyStocks_AddNewItem() throws ExceptionHandling {

        //given
        String testInput = "1\nF 50\n";
        setInput(testInput);
        //when
        UIMapper.modifyStocks(stockItems);

        //then
        //no error
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());  //validate the increment in size of list
        assertEquals("F 50", getOutput());
        stockItems.removeStockItem('F', ITEMS_FILE);
    }

    @Test
    public void modifyStocks_AndRemoveNonFoundItem() {
        //given
        String testInput = "2\nZ 3\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ITEM_NOTFOND, getOutput());
    }

    @Test
    public void modifyStocks_AndRemoveNullItem() {
        //given
        String testInput = "2\n \n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ITEM_NOTFOND, getOutput());
    }

    @Test
    public void modifyStocks_AndRemoveAnItem() {

        //given
        String testInput = "1\nF 50\n";
        setInput(testInput);
        //when
        UIMapper.modifyStocks(stockItems);
        testInput = "2\nF\n";
        setInput(testInput);
        //then
        UIMapper.modifyStocks(stockItems);
        assertEquals("Successfully Deleted, Specified Stock...", getOutput());
    }

    @Test
    public void modifySpecialPrice_WithInvalidOptionChar() {
        //given
        String testInput = "A";
        setInput(testInput);
        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);

        //then
        assertEquals(NUMERIC_ERROR, getOutput());
    }

    @Test
    public void modifySpecialPrice_WithInvalidOption() {
        //given
        String testInput = "3";
        setInput(testInput);
        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);

        //then
        assertEquals(INVALID_OPTION, getOutput());
    }

    @Test
    public void modifySpecialPrice_AddSpecialRule_WithInValidInputFormat() {
        //given
        String testInput = "1\nA 4\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ALLDETAILS_REQUIRED, getOutput());
    }

    @Test
    public void modifySpecialPrice_AddSpecialRule_WithInvalidInputOnlyLetters() {
        //given
        String testInput = "1\nAA\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, getOutput());
    }

    @Test
    public void modifySpecialPrice_AddSpecialRule_WithAZeroDiscountPrice() {
        //given
        String testInput = "1\nA 6 0\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(PRICE_ZERO, getOutput());
    }

    @Test
    public void modifySpecialPrice_AddSpecialRule_WithAValidInput() throws ExceptionHandling {
        //given
        String testInput = "1\nA 6 90\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals("A 6 90", getOutput());
        specialOffers.removeSpecialOffer('A', 6, SPECIALPRICES_FILE);
    }

    @Test
    public void modifySpecialPrice_AddSpecialRule_WithExistingRule() {
        //given
        String testInput = "1\nA 3 90\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(PRICINGRULE_EXIST, getOutput());
    }

    @Test
    public void modifySpecialPrice_AndRemoveSpecialRule_WithInValidInputFormat() {
        //given
        String testInput = "2\nA A\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, getOutput());
    }

    @Test
    public void modifySpecialPrice_AndRemoveSpecialRule_WithInvalidInput() {
        //given
        String testInput = "2\nA 4 A\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ALLDETAILS_REQUIRED, getOutput());
    }

    @Test
    public void modifySpecialPrice_AndRemoveSpecialRule_WithNoneExistingRule() {
        //given
        String testInput = "2\nA 6\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(PRICINGRULE_NOTPRESENT, getOutput());
    }

    @Test
    public void modifySpecialPrice_AndRemoveSpecialRule() {

        //given
        String testInput = "1\nA 5 180\n";
        setInput(testInput);
        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        testInput = "2\nA 5\n";
        setInput(testInput);
        //then
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        assertEquals("Successfully Deleted, Specified Rule...", getOutput());
    }


}