package com.challenge.checkoutKata.shopping;

import com.challenge.checkoutKata.stock.StockItems;
import com.challenge.checkoutKata.util.ExceptionHandling;
import com.challenge.checkoutKata.util.Utility;
import com.challenge.checkoutKata.wholesale.SpecialOffers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.challenge.checkoutKata.util.Constants.ITEM_NOTFOND;
import static com.challenge.checkoutKata.util.Constants.NULLFOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasketTest {
    private Basket basket;
    private StockItems stockItems;
    private SpecialOffers specialOffers;
    private static Utility utility;

    @BeforeEach()
    public void setUp() throws ExceptionHandling {
        //given
        utility = new Utility();
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
        utility.initialise(stockItems, specialOffers);
        basket = new Basket(stockItems);
    }
    @AfterEach
    public void cleanUp() {
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
    }

    @Test
    public void validate_addItemToBasket() throws ExceptionHandling {
        //when
        basket.addItemToBasket("AAAABBBBBBBBBBBB");

        //then
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(2, basket.getItemsTotal().size());
    }

    @Test
    public void validate_nullItemInBasket(){

        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
                basket.addItemToBasket(null)
        );
        //then
        assertEquals(NULLFOUND, exception.getMessage());
        assertEquals(0, basket.getShoppingBasket().size());
        assertEquals(0, basket.getItemsTotal().size());

    }

    @Test
    public void validate_UnknownItemInBasket(){

        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //when
                basket.addItemToBasket("AABCCCZZZZZ")
        );

        //then
        assertEquals(ITEM_NOTFOND + ": Z", exception.getMessage());
        assertEquals(3, basket.getShoppingBasket().size());
        assertEquals(3, basket.getItemsTotal().size());

    }

}