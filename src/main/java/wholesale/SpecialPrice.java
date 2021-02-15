package wholesale;

import stock.Product;

import java.math.BigDecimal;

public class SpecialPrice {

    private final Product product;    //Has-A relationship through composition
    private final BigDecimal discountPrice;
    private final int noOfItems;

    public SpecialPrice(Product product, int noOfItems, BigDecimal discountPrice) {
        this.product = product;
        this.discountPrice = discountPrice;
        this.noOfItems = noOfItems;
    }

    public Product getStockItem() { return product; }

    public BigDecimal getDiscountPrice() { return discountPrice; }

    public int getNoOfItems() { return noOfItems; }
}
