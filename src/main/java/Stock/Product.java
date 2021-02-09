package Stock;

public class Product {
    private char name;
    private float unitPrice;

    public Product(char name, float unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public char getName() { return name; }
    public float getUnitPrice() { return unitPrice; }

}
