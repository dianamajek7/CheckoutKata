public class SpecialPrice {

    private StockItem stockItem;
    private float discountPrice;
    private int noOfItem;

    public SpecialPrice(StockItem stockItem, int noOfItem, float discountPrice) {
        this.stockItem = stockItem;
        this.discountPrice = discountPrice;
        this.noOfItem = noOfItem;
    }

    public StockItem getStockItem() { return stockItem; }

    public float getDiscountPrice() { return discountPrice; }

    public int getNoOfItem() { return noOfItem; }
}
