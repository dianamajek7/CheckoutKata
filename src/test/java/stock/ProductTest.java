package stock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductTest {
    @Test
    public void validateStockItem_ContainsAUniqueCode() {
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('B', new BigDecimal(30));
        assertNotEquals(product.getName(), product2.getName());
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

    @Test
    public void validateProductEquals() {
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(50));
        assertEquals(product2, product);
    }

    @Test
    public void validateProductNotEqual() {
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(80));
        assertNotEquals(product2, product);
    }

    @Test
    public void validateProductHashCodeEquality() {
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(50));
        assertEquals(product.hashCode(), product2.hashCode());
    }

    @Test
    public void validateIdenticalProductName_WithUniqueUnitPrice_HashCode() {
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('A', new BigDecimal(20));
        assertNotEquals(product.hashCode(), product2.hashCode());
    }

    @Test
    public void validateDifferentProduct_HashCode() {
        Product product = new Product('A', new BigDecimal(50));
        Product product2 = new Product('C', new BigDecimal(80));
        assertNotEquals(product.hashCode(), product2.hashCode());
    }
}