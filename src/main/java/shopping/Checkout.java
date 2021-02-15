package shopping;

import stock.Product;
import util.Utility;
import wholesale.SpecialOffers;
import wholesale.SpecialPrice;

import java.util.List;
import java.util.Map;

public class Checkout {

    private float total;
    SpecialOffers specialOffers;

    public Checkout(SpecialOffers specialOffers) {
        this.specialOffers = specialOffers;
        this.total = 0;
    }

    public float getTotal() { return total; }

    public void calculateTotal(Map<Product, Integer> shoppingBasket) {
        int calcTotal = 0;
        int itemTotal;


        //calculates each item in the basket total price and applies discount if appropriate
        for(Map.Entry<Product, Integer> basket : shoppingBasket.entrySet()) {
            int unitPrice = basket.getKey().getUnitPrice();
            char itemName = basket.getKey().getName();
            int noOfOccurrence = basket.getValue();

            itemTotal = unitPrice * noOfOccurrence;

            //filters the list of whole sale items to find the matching itemName
            List<SpecialPrice> filteredPriceRule = Utility.filter(specialOffers.getSpecialOffers(), e-> e.getStockItem().getName() == itemName && noOfOccurrence >= e.getNoOfItems());
            itemTotal = applySpecialPrice(filteredPriceRule, itemTotal, noOfOccurrence, unitPrice);


            calcTotal += itemTotal;
        }
        this.total = (float)calcTotal;  //returns a cast of a float from a primitive type int
    }

    private int applySpecialPrice(List<SpecialPrice> filteredPriceRule, int itemTotal, int noOfOccurrence, int unitPrice){
        if(filteredPriceRule.size() == 1) {
            //item is eligible for discount if the noOfOccurrence is greater or equal to nofItems in wholesale offer
            SpecialPrice specialPrice = filteredPriceRule.stream().findFirst().get();
            int discountPrice = specialPrice.getDiscountPrice();
            int noOfItemsOnSale = specialPrice.getNoOfItems();

            if(noOfItemsOnSale == noOfOccurrence) {
                itemTotal = discountPrice;  //returns price if noOfItem matches wholesale quantity
            } else{
                int priceDifference = (unitPrice * noOfItemsOnSale) - discountPrice;  // the difference between original and wholesale price
                int itemReduced = noOfOccurrence / noOfItemsOnSale;  //reduced item deducted from wholesale quantity

                itemTotal -= priceDifference * itemReduced ;    //subtracts the difference from the total price of an item
            }
        }
        return  itemTotal;
    }


}
