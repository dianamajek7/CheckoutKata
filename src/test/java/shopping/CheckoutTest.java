package shopping;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stock.StockItems;
import util.Utility;
import wholesale.SpecialOffers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CheckoutTest {
    Basket basket;
    Checkout checkout;
    StockItems stockItems;
    SpecialOffers specialOffers;

    @BeforeEach()
    public void setUp(){
        //given
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
        Utility.initialise(stockItems, specialOffers);
        basket = new Basket(stockItems);
        checkout = new Checkout(specialOffers);

    }

    @AfterEach
    public void cleanUp() {
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
    }

    @Test
    public void validate_calcTotal_WithEmptyValue_InBasket(){
        //when
        String msg = basket.addItemToBasket("");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(0, basket.getShoppingBasket().size());
        assertEquals(0, basket.getItemsTotal().size());
        assertEquals(0, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithNoneDiscountAndDiscountItems_WithOccurrenceOfThree_InBasket(){
        //when
        String msg = basket.addItemToBasket("ABB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(95, checkout.getTotal());
        assertEquals(2, basket.getItemsTotal().size());
    }


    @Test
    public void validate_calcTotal_WithOneItem_Discount_InBasket(){
        //when
        String msg = basket.addItemToBasket("A");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(50f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithOneItem_NoneDiscount_InBasket(){
        //when
        String msg = basket.addItemToBasket("D");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(15f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithTwo_NoneDiscountAndDiscountItems_InBasket(){
        //when
        String msg = basket.addItemToBasket("AB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(80f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithFour_NoneDiscountAndDiscountItems_InBasket(){
        //when
        String msg = basket.addItemToBasket("CDBA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(4, basket.getShoppingBasket().size());
        assertEquals(115f, checkout.getTotal());
        assertEquals(4, basket.getItemsTotal().size());
    }

    @Test
    public void validate_calcTotal_WithNoneDiscountItem_WithOccurrenceOfTwo_InBasket(){
        //when
        String msg = basket.addItemToBasket("AA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(100f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscountItem_WithOccurrenceOfThree_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(130f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscount_AndNoneDiscountItem_WithOccurrenceOfFour_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(180f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscount_AndNoneDiscountItem_WithOccurrenceOfFive_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAAAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(230f, checkout.getTotal());
        assertEquals(1, basket.getItemsTotal().size());
    }

    @Test
    public void validate_calcTotal_WithDiscountItem_WithOccurrenceOfSix_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAAAAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(260f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDifferentDiscountItem_WithOccurrenceOfSix_InBasket(){
        //when
        String msg = basket.addItemToBasket("BBBBBB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(135, checkout.getTotal());
        assertEquals(1, basket.getItemsTotal().size());
    }

    @Test
    public void validate_calcTotal_WithDifferentDiscountItems_WitOccurrenceOfSix_InBasket(){
        //when
        String msg = basket.addItemToBasket("BBBBBBAAAAAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(395, checkout.getTotal());
        assertEquals(2, basket.getItemsTotal().size());
    }

    @Test
    public void validate_calcTotal_WithNoneDiscountAndDiscountItems_WithOccurrenceOfFour_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAAB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(160f, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscountItems_AndOccurrenceOfFiveInBasket(){
        //when
        String msg = basket.addItemToBasket("AAABB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(175, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithMoreDiscountItem_AndANoneDiscountItem_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAABBD");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(3, basket.getShoppingBasket().size());
        assertEquals(190, checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithSix_NeutralNoneDiscountAndDiscountItem_InBasket(){
        //when
        String msg = basket.addItemToBasket("DABABA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(3, basket.getShoppingBasket().size());
        assertEquals(190, checkout.getTotal());
        assertEquals(3, basket.getItemsTotal().size());
    }

}