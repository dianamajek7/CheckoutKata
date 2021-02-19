package com.challenge.checkoutKata;

import com.challenge.checkoutKata.shopping.Basket;
import com.challenge.checkoutKata.shopping.Checkout;
import com.challenge.checkoutKata.stock.StockItems;
import com.challenge.checkoutKata.util.ExceptionHandling;
import com.challenge.checkoutKata.util.Utility;
import com.challenge.checkoutKata.wholesale.SpecialOffers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static com.challenge.checkoutKata.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UIMapperTest {
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
    public void validate_Shopping_WithInvalidFormat() {
        //given
        String testInput = "A B C D";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void validate_Shopping_WithCharacterInput() {
        //given
        String testInput = "/";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void validate_Shopping_WithCharactersAndNumbersInput() {
        //given
        String testInput = "AA89";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void validate_Shopping_WithNumbersInput() {
        //given
        String testInput = "4334";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ONLY_LETTERS_ALLOWED, getOutput());
    }

    @Test
    public void validate_Shopping_WithItem_NotFound() {
        //given
        String testInput = "ZZ";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then expected error
        assertEquals(ITEM_NOTFOND + ": Z", getOutput());
    }

    @Test
    public void validate_Shopping_WithValidItems() {
        //given
        String testInput = "ABB";
        setInput(testInput);
        //when
        UIMapper.shopping(basket, checkout, specialOffers);

        //then
        assertEquals("Total price: 95", getOutput());
    }

    @Test
    public void validate_OutputFileContent_EmptyFile() throws IOException {
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
    public void validate_ModifyStocks_WithInvalidOptionChar() {
        //given
        String testInput = "A";
        setInput(testInput);
        //when
        UIMapper.modifyStocks(stockItems);

        //then
        assertEquals(NUMERIC_ERROR, getOutput());
    }

    @Test
    public void validate_ModifyStocks_AddItem_WithValidInput() {
        //given
        String testInput = "1\nA 4\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ITEM_EXIST, getOutput());
    }

    @Test
    public void validate_ModifyStocks_WithAddItemZeroPrice() {
        //given
        String testInput = "1\nA 0\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(PRICE_ZERO, getOutput());
    }

    @Test
    public void validate_ModifyStocks_AddItemName() {
        //given
        String testInput = "1\nA3\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, getOutput());
    }

    @Test
    public void validate_ModifyStocks_WithNoneExistingOption() {
        //given
        String testInput = "3\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(INVALID_OPTION, getOutput());
    }

    @Test
    public void validate_ModifyStocks_AddNewItem() throws ExceptionHandling {

        //given
        String testInput = "1\nF 50\n";
        setInput(testInput);
        //when
        UIMapper.modifyStocks(stockItems);

        //then
        //no error, validate the increment in size of list
        assertEquals(utility.readInputFromResource(ITEMS).size() + 1, stockItems.getProducts().size());
        assertEquals("F 50", getOutput());
        stockItems.removeStockItem('F', ITEMS_FILE);
    }

    @Test
    public void validate_ModifyStocks_AndRemoveNonFoundItem() {
        //given
        String testInput = "2\nZ 3\n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ITEM_NOTFOND, getOutput());
    }

    @Test
    public void validate_ModifyStocks_AndRemoveNullItem() {
        //given
        String testInput = "2\n \n";
        setInput(testInput);

        //when
        UIMapper.modifyStocks(stockItems);
        //then
        assertEquals(ITEM_NOTFOND, getOutput());
    }

    @Test
    public void validate_ModifyStocks_AndRemoveAnItem() {

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
    public void validate_ModifySpecialPrice_WithInvalidOptionChar() {
        //given
        String testInput = "A";
        setInput(testInput);
        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);

        //then
        assertEquals(NUMERIC_ERROR, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_WithInvalidOption() {
        //given
        String testInput = "3";
        setInput(testInput);
        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);

        //then
        assertEquals(INVALID_OPTION, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AddSpecialRule_WithInValidInputFormat() {
        //given
        String testInput = "1\nA 4\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ALLDETAILS_REQUIRED, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AddSpecialRule_WithInvalidInputOnlyLetters() {
        //given
        String testInput = "1\nAA\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AddSpecialRule_WithAZeroDiscountPrice() {
        //given
        String testInput = "1\nA 6 0\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(PRICE_ZERO, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AddSpecialRule_WithAValidInput() throws ExceptionHandling {
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
    public void validate_ModifySpecialPrice_AddSpecialRule_WithExistingRule() {
        //given
        String testInput = "1\nA 3 90\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(PRICINGRULE_EXIST, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AndRemoveSpecialRule_WithInValidInputFormat() {
        //given
        String testInput = "2\nA A\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AndRemoveSpecialRule_WithInvalidInput() {
        //given
        String testInput = "2\nA 4 A\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(ALLDETAILS_REQUIRED, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AndRemoveSpecialRule_WithNoneExistingRule() {
        //given
        String testInput = "2\nA 6\n";
        setInput(testInput);

        //when
        UIMapper.modifySpecialPrice(stockItems, specialOffers);
        //then
        assertEquals(PRICINGRULE_NOTPRESENT, getOutput());
    }

    @Test
    public void validate_ModifySpecialPrice_AndRemoveSpecialRule() {

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