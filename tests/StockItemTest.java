import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StockItemTest {

    @Test
        public void validateStockItem_ContainsAUniqueCode() {
        StockItem stockItem = new StockItem('A', 50);
        StockItem stockItem1 = new StockItem('B', 30);
        assertNotEquals(stockItem.getName(), stockItem1.getName());
    }

    @Test
    public void validateStockItem_Code() {
        StockItem stockItem = new StockItem('A', 50);
        assertEquals('A', stockItem.getName());
    }

    @Test
    public void validateStockItem_UnitPrice() {
        StockItem stockItem = new StockItem('A', 50);
        assertEquals(50, stockItem.getUnitPrice());
    }
}
