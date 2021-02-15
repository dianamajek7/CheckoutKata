package stock;

import java.math.BigDecimal;

public class Product {
    private final char name;
    private final BigDecimal unitPrice;

    public Product(char name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public char getName() { return name; }
    public BigDecimal getUnitPrice() { return unitPrice; }

}
