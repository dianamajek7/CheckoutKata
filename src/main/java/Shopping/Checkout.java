package Shopping;

import Stock.Product;
import Stock.StockItems;
import Wholesale.SpecialOffers;
import Wholesale.SpecialPrice;
import util.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Shopping.BasketUtil.initialiseWholeSale;

public class Checkout {

    private float total;

    StockItems stockItems;
    SpecialOffers specialOffers;

    public Checkout() {
        this.specialOffers = new SpecialOffers();
        this.stockItems = new StockItems();
        initialiseWholeSale(stockItems, specialOffers);
        this.total = 0f;
    }

    public float getTotal() { return total; }

    public void calculateTotal(Map<Product, Integer> shoppingBasket) {
        int calcTotal = 0;
        int itemTotal;


        //calculates each item in the basket total price and applies discount if appropriate
        for(Map.Entry<Product, Integer> basket : shoppingBasket.entrySet()) {
            int unitPrice = (int) basket.getKey().getUnitPrice();
            char itemName = basket.getKey().getName();
            int noOfOccurrence = basket.getValue();

            itemTotal = unitPrice * noOfOccurrence;

            List<SpecialPrice> filteredPriceRule = Utility.filter(specialOffers.getSpecialOffers(), e-> e.getStockItem().getName() == itemName && noOfOccurrence >= e.getNoOfItems());
            itemTotal = applySpecialPrice(filteredPriceRule, itemTotal, noOfOccurrence, unitPrice);


            calcTotal += itemTotal;
        }
        this.total = (float)calcTotal;
    }

    private int applySpecialPrice(List<SpecialPrice> filteredPriceRule, int itemTotal, int noOfOccurrence, int unitPrice){
        if(filteredPriceRule.size() == 1) {
            //item is eligible for discount if the noOfOccurrence is greater or equal to nofItems in offer
            SpecialPrice specialPrice = filteredPriceRule.stream().findFirst().get();
            int discountPrice = (int) specialPrice.getDiscountPrice();
            int noOfItems = specialPrice.getNoOfItems();

            if(noOfItems == noOfOccurrence) {
                itemTotal = discountPrice;  //returns price if noOfItem matches wholesale quantity
            } else{
                int priceDifference = (unitPrice * noOfItems) - discountPrice;  // the difference between original and wholesale price
                int itemReduced = noOfOccurrence / noOfItems;  //reduced item deducted from wholesale quantity

                itemTotal -= priceDifference * itemReduced ;



            }
        }
        return  itemTotal;
    }


}
