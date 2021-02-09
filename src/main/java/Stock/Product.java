package Stock;

import Wholesale.SpecialPrice;

import java.util.logging.Logger;

public class Product {
    private static final Logger LOGGER = Logger.getLogger( Product.class.getName() );
    private char name;
    private float unitPrice;

    public Product(char name, float unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public char getName() { return name; }
    public float getUnitPrice() { return unitPrice; }

}
