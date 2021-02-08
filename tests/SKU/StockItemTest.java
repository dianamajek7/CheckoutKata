package SKU;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void validateStockItem_Code() {
        StockItem stockItem = new StockItem('A', 50);
        assertEquals('A', stockItem.getItem());
    }

    @Test
    public void validateStockItem_UnitPrice() {
        StockItem stockItem = new StockItem('A', 50);
        assertEquals(50, stockItem.getUnitPrice());
    }
}
