package SKU;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StockItemTest {


    @Test
    public void validateStockItem_ContainsAnIdentifier() {
        StockItem stockItem = new StockItem('A', 50);
        assertNotNull(stockItem.getItemId());
    }

    @Test
    public void validateStockItem_ContainsAUniqueIdentifier() {
        StockItem stockItem = new StockItem('A', 50);
        StockItem stockItem1 = new StockItem('B', 30);
        assertNotEquals(stockItem, stockItem1);
    }
}
