package wholesale;

import org.junit.jupiter.api.Test;
import stock.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SpecialPriceTest {
    @Test
    public void validateSpecialPrice_WithAnItem() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //when
        SpecialPrice specialPrice = new SpecialPrice(product, 3, new BigDecimal(130));

        //then
        assertEquals('A', specialPrice.getStockItem().getName());
        assertEquals(new BigDecimal(130), specialPrice.getDiscountPrice());
        assertEquals(3, specialPrice.getNoOfItems());
    }

    @Test
    public void validateSpecialPriceEquals_WithIdenticalItem() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //when
        SpecialPrice specialPrice = new SpecialPrice(product, 3, new BigDecimal(130));
        SpecialPrice specialPrice2 = new SpecialPrice(product, 3, new BigDecimal(130));

        //then
        assertEquals(specialPrice2, specialPrice);
    }

    @Test
    public void validateSpecialPriceNotEquals_WithIdenticalItemButUniqueNoOfItems() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //when
        SpecialPrice specialPrice = new SpecialPrice(product, 3, new BigDecimal(130));
        SpecialPrice specialPrice2 = new SpecialPrice(product, 4, new BigDecimal(130));

        //then
        assertNotEquals(specialPrice2, specialPrice);
    }

    @Test
    public void validateSpecialPriceEquals_HashCode() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //when
        SpecialPrice specialPrice = new SpecialPrice(product, 3, new BigDecimal(130));
        SpecialPrice specialPrice2 = new SpecialPrice(product, 3, new BigDecimal(130));

        //then
        assertEquals(specialPrice2.hashCode(), specialPrice.hashCode());
    }

    @Test
    public void validateSpecialPriceNotEquals_HashCode() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //when
        SpecialPrice specialPrice = new SpecialPrice(product, 3, new BigDecimal(130));
        SpecialPrice specialPrice2 = new SpecialPrice(product, 4, new BigDecimal(130));

        //then
        assertNotEquals(specialPrice2.hashCode(), specialPrice.hashCode());
    }

    @Test
    public void validateSpecialPriceNotEquals_WithIdenticalItemButUniqueDiscountPrice() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        //when
        SpecialPrice specialPrice = new SpecialPrice(product, 3, new BigDecimal(130));
        SpecialPrice specialPrice2 = new SpecialPrice(product, 3, new BigDecimal(170));

        //then
        assertNotEquals(specialPrice2, specialPrice);
    }

    @Test
    public void validateSpecialPrice_With_MultipleItems() {
        //given
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('B', new BigDecimal(30));
        //when
        SpecialPrice specialPrice = new SpecialPrice(product, 3, new BigDecimal(130));
        SpecialPrice specialPrice2 = new SpecialPrice(product2, 2, new BigDecimal(45));
        //then
        assertEquals('A', specialPrice.getStockItem().getName());
        assertEquals(new BigDecimal(130), specialPrice.getDiscountPrice());
        assertEquals(3, specialPrice.getNoOfItems());

        assertEquals('B', specialPrice2.getStockItem().getName());
        assertEquals(new BigDecimal(45), specialPrice2.getDiscountPrice());
        assertEquals(2, specialPrice2.getNoOfItems());
    }
}