public class SpecialPrice {

    private StockItem stockItem;    //Has-A relationship through composition
    private float discountPrice;
    private int noOfItems;

    public SpecialPrice(StockItem stockItem, int noOfItems, float discountPrice) {
        this.stockItem = stockItem;
        this.discountPrice = discountPrice;
        this.noOfItems = noOfItems;
    }

    public StockItem getStockItem() { return stockItem; }

    public float getDiscountPrice() { return discountPrice; }

    public int getNoOfItems() { return noOfItems; }
}
