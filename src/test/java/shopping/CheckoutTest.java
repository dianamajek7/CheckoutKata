package shopping;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stock.StockItems;
import util.Utility;
import wholesale.SpecialOffers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CheckoutTest {
    Basket basket;
    Checkout checkout;
    StockItems stockItems;
    SpecialOffers specialOffers;
    static Utility utility;

    @BeforeEach()
    public void setUp(){
        //given
        utility = new Utility();
        stockItems = new StockItems();
        specialOffers = new SpecialOffers();
        utility.initialise(stockItems, specialOffers);
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
        assertEquals(new BigDecimal(0), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithNoneDiscountAndDiscountItems_WithOccurrenceOfThree_InBasket(){
        //when
        String msg = basket.addItemToBasket("ABB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(95), checkout.getTotal());
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
        assertEquals(new BigDecimal(50), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithOneItem_NoneDiscount_InBasket(){
        //when
        String msg = basket.addItemToBasket("D");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(15), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithTwo_NoneDiscountAndDiscountItems_InBasket(){
        //when
        String msg = basket.addItemToBasket("AB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(80), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithFour_NoneDiscountAndDiscountItems_InBasket(){
        //when
        String msg = basket.addItemToBasket("CDBA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(4, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(115), checkout.getTotal());
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
        assertEquals(new BigDecimal(100), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscountItem_WithOccurrenceOfThree_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(130), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscount_AndNoneDiscountItem_WithOccurrenceOfFour_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(180), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscount_AndNoneDiscountItem_WithOccurrenceOfFive_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAAAA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(230), checkout.getTotal());
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
        assertEquals(new BigDecimal(260), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDifferentDiscountItem_WithOccurrenceOfSix_InBasket(){
        //when
        String msg = basket.addItemToBasket("BBBBBB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(1, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(135), checkout.getTotal());
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
        assertEquals(new BigDecimal(395), checkout.getTotal());
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
        assertEquals(new BigDecimal(160), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithDiscountItems_AndOccurrenceOfFiveInBasket(){
        //when
        String msg = basket.addItemToBasket("AAABB");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(2, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(175), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithMoreDiscountItem_AndANoneDiscountItem_InBasket(){
        //when
        String msg = basket.addItemToBasket("AAABBD");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(3, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(190), checkout.getTotal());
    }

    @Test
    public void validate_calcTotal_WithSix_NeutralNoneDiscountAndDiscountItem_InBasket(){
        //when
        String msg = basket.addItemToBasket("DABABA");
        checkout.calculateTotal(basket.getShoppingBasket());
        //then
        assertNull(msg);
        assertEquals(3, basket.getShoppingBasket().size());
        assertEquals(new BigDecimal(190), checkout.getTotal());
        assertEquals(3, basket.getItemsTotal().size());
    }

}