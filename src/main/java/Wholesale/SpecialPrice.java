package Wholesale;

import Stock.Product;

public class SpecialPrice {

    private Product product;    //Has-A relationship through composition
    private int discountPrice;
    private int noOfItems;

    public SpecialPrice(Product product, int noOfItems, int discountPrice) {
        this.product = product;
        this.discountPrice = discountPrice;
        this.noOfItems = noOfItems;
    }

    public Product getStockItem() { return product; }

    public int getDiscountPrice() { return discountPrice; }

    public int getNoOfItems() { return noOfItems; }
}
