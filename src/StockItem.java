public class StockItem {

    private char name;
    private float unitPrice;

    public StockItem(char name, float unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public char getName() { return name; }
    public float getUnitPrice() { return unitPrice; }

}
