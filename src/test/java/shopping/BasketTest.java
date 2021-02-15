package shopping;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stock.StockItems;
import util.Utility;
import wholesale.SpecialOffers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static util.Constants.ITEM_NOTFOND;
import static util.Constants.NULLFOUND;

class BasketTest {
    Basket basket;
    StockItems stockItems;
    SpecialOffers specialOffers;

    @BeforeEach()
    public void setUp(){
        //given
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
        Utility.initialise(stockItems, specialOffers);
        basket = new Basket(stockItems);
    }
    @AfterEach
    public void cleanUp() {
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
    }

    @Test
    public void validate_addItemToBasket() {
        //when
        String msg = basket.addItemToBasket("AAAABBBBBBBBBBBB");

        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(2, basket.getItemsTotal().size());
    }

    @Test
    public void validate_nullItemInBasket(){
        //when
        String msg = basket.addItemToBasket(null);
        //then
        assertEquals(NULLFOUND, msg);
        assertEquals(0, basket.getShoppingBasket().size());
        assertEquals(0, basket.getItemsTotal().size());
    }

    @Test
    public void validate_UnknownItemInBasket(){
        //when
        String msg = basket.addItemToBasket("AABCCCZZZZZ");
        //then
        assertEquals(3, basket.getShoppingBasket().size());
        assertEquals(ITEM_NOTFOND  + ": Z", msg);
        assertEquals(3, basket.getItemsTotal().size());

    }

}