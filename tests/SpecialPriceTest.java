import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SpecialPriceTest {
    @Test
    public void validateSpecialPrice_WithAnItem() {
        //given
        StockItem stockItem = new StockItem('A', 50);
        SpecialPrice specialPrice = new SpecialPrice(stockItem, 3, 130f);

        //then
        assertEquals('A', specialPrice.getStockItem().getName());
        assertEquals(130f, specialPrice.getDiscountPrice());
        assertEquals(3, specialPrice.getNoOfItem());
    }

    @Test
    public void validateSpecialPrice_With_MultipleItem() {
        StockItem stockItem = new StockItem('A', 50);
        StockItem stockItem2 = new StockItem('B', 30);
        SpecialPrice specialPrice = new SpecialPrice(stockItem, 3, 130f);
        SpecialPrice specialPrice2 = new SpecialPrice(stockItem2, 2, 45f);

        assertEquals('A', specialPrice.getStockItem().getName());
        assertEquals(130f, specialPrice.getDiscountPrice());
        assertEquals(3, specialPrice.getNoOfItem());

        assertEquals('B', specialPrice2.getStockItem().getName());
        assertEquals(45f, specialPrice2.getDiscountPrice());
        assertEquals(2, specialPrice2.getNoOfItem());
    }


}
