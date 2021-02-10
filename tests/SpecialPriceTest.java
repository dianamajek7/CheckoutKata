import Stock.Product;
import Wholesale.SpecialPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialPriceTest {
    @Test
    public void validateSpecialPrice_WithAnItem() {
        //given
        Product product = new Product('A', 50);
        SpecialPrice specialPrice = new SpecialPrice(product, 3, 130);

        //then
        assertEquals('A', specialPrice.getStockItem().getName());
        assertEquals(130f, specialPrice.getDiscountPrice());
        assertEquals(3, specialPrice.getNoOfItems());
    }

    @Test
    public void validateSpecialPrice_With_MultipleItems() {
        Product product = new Product('A', 50);
        Product product2 = new Product('B', 30);
        SpecialPrice specialPrice = new SpecialPrice(product, 3, 130);
        SpecialPrice specialPrice2 = new SpecialPrice(product2, 2, 45);

        assertEquals('A', specialPrice.getStockItem().getName());
        assertEquals(130f, specialPrice.getDiscountPrice());
        assertEquals(3, specialPrice.getNoOfItems());

        assertEquals('B', specialPrice2.getStockItem().getName());
        assertEquals(45f, specialPrice2.getDiscountPrice());
        assertEquals(2, specialPrice2.getNoOfItems());
    }


}
