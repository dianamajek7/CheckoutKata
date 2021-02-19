package com.challenge.checkoutKata;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static com.challenge.checkoutKata.util.Constants.INVALID_OPTION;
import static com.challenge.checkoutKata.util.Constants.NUMERIC_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UITest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() {
        //initialise the test output stream
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
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
    public void validate_main_WithNoneIntegerOption() {
        //given
        String testInput = "n";
        setInput(testInput);
        String[] args = {};
        //when
        UI.main(args);

        assertEquals(NUMERIC_ERROR, getOutput());

    }

    @Test
    public void validate_main_WithNoneInvalidOption() {
        //given
        String testInput = "6";
        setInput(testInput);
        String[] args = {};
        //when
        UI.main(args);

        assertEquals(INVALID_OPTION, getOutput());

    }

    @Test
    public void validate_main_WithNShoppingOption() {
        //given
        String testInput = "1";
        setInput(testInput);

        assertThrows(Exception.class, () -> {
            //when
            String[] args = {};
            UI.main(args);
        });
        //then
        assertEquals("Add items to your basket i.e- AAAB: ", getOutput());
    }

    @Test
    public void validate_main_WithModifyStocksOption() {
        //given
        String testInput = "2";
        setInput(testInput);

        assertThrows(Exception.class, () -> {
            //when
            String[] args = {};
            UI.main(args);
        });
        //then
        assertEquals("Enter 2 to remove an Item...", getOutput());
    }

    @Test
    public void validate_main_WithModifySpecialPriceOption() {
        //given
        String testInput = "3";
        setInput(testInput);

        assertThrows(Exception.class, () -> {
            //when
            String[] args = {};
            UI.main(args);
        });
        //then
        assertEquals("Enter 2 to remove a Price Rule...", getOutput());
    }
}