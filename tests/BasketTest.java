import Shopping.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static util.Constants.ITEM_NOTFOND;
import static util.Constants.NULLFOUND;

public class BasketTest {
    Basket basket;

    @BeforeEach()
    public void setUp(){
        //given
        basket = new Basket();
    }

    @Test
    public void validate_addItemToBasket() {
        //when
        String msg = basket.addItemToBasket("AAAABBBBBBBBBBBB");

        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
    }

    @Test
    public void validate_nullItemInBasket(){
        //when
        String msg = basket.addItemToBasket(null);
        //then
        assertEquals(NULLFOUND, msg);

    }

    @Test
    public void validate_UnknownItemInBasket(){
        //when
        String msg = basket.addItemToBasket("AABCCCZZZZZ");
        //then
        assertEquals(3, basket.getShoppingBasket().size());
        assertEquals(ITEM_NOTFOND  + ": Z", msg);

    }
}
