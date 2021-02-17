package wholesale;

import stock.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class SpecialPrice {

    private final Product stockItem;    //Has-A relationship through composition
    private final BigDecimal discountPrice;
    private final int noOfItems;

    public SpecialPrice(Product stockItem, int noOfItems, BigDecimal discountPrice) {
        this.stockItem = stockItem;
        this.discountPrice = discountPrice;
        this.noOfItems = noOfItems;
    }

    public Product getStockItem() { return stockItem; }

    public BigDecimal getDiscountPrice() { return discountPrice; }

    public int getNoOfItems() { return noOfItems; }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof SpecialPrice)) return false;

        SpecialPrice specialPrice = (SpecialPrice) o;
        return this.getNoOfItems() == specialPrice.getNoOfItems() &&
                this.getStockItem().equals(specialPrice.getStockItem()) &&
                this.getDiscountPrice().equals(specialPrice.getDiscountPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getStockItem(), this.getDiscountPrice(), this.getNoOfItems());
    }
}
