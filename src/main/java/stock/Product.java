package stock;

public class Product {
    private char name;
    private int unitPrice;

    public Product(char name, int unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public char getName() { return name; }
    public int getUnitPrice() { return unitPrice; }

}
