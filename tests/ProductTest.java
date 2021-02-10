import Stock.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductTest {

    @Test
        public void validateStockItem_ContainsAUniqueCode() {
        Product product = new Product('A', 50f);
        Product product1 = new Product('B', 30f);
        assertNotEquals(product.getName(), product1.getName());
    }

    @Test
    public void validateStockItem_Code() {
        Product product = new Product('A', 50f);
        assertEquals('A', product.getName());
    }

    @Test
    public void validateStockItem_UnitPrice() {
        Product product = new Product('A', 50f);
        assertEquals(50, product.getUnitPrice());
    }
}
