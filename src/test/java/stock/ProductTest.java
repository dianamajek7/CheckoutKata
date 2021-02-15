package stock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductTest {
    @Test
    public void validateStockItem_ContainsAUniqueCode() {
        Product product = new Product('A', new BigDecimal(50));
        Product product1 = new Product('B', new BigDecimal(30));
        assertNotEquals(product.getName(), product1.getName());
    }

    @Test
    public void validateStockItem_Code() {
        Product product = new Product('A', new BigDecimal(50));
        assertEquals('A', product.getName());
    }

    @Test
    public void validateStockItem_UnitPrice() {
        Product product = new Product('A', new BigDecimal(50));
        assertEquals(new BigDecimal(50), product.getUnitPrice());
    }

}