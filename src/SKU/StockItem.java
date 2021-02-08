package SKU;

import java.util.UUID;

public class StockItem {
    private char item;
    private String itemId;
    private float unitPrice;

    public StockItem(char item, float unitPrice) {
        this.item = item;
        this.itemId = UUID.randomUUID().toString(); //initializing a unique random identifier for each stock
        this.unitPrice = unitPrice;
    }

    public String getItemId() {
        return itemId;
    }

}
