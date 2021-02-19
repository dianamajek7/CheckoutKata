package com.challenge.checkoutKata.shopping;

import com.challenge.checkoutKata.stock.Product;
import com.challenge.checkoutKata.wholesale.SpecialOffers;
import com.challenge.checkoutKata.wholesale.SpecialPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Checkout {

    private BigDecimal total;
    SpecialOffers specialOffers;

    public Checkout(SpecialOffers specialOffers) {
        this.specialOffers = specialOffers;
        this.total = new BigDecimal(0);
    }

    public BigDecimal getTotal() { return total; }

    public void calculateTotal(Map<Product, Integer> shoppingBasket) {
        BigDecimal calcTotal = new BigDecimal(0);
        BigDecimal itemTotal;


        //calculates each item in the basket total price and applies discount if appropriate
        for(Map.Entry<Product, Integer> basket : shoppingBasket.entrySet()) {
            BigDecimal unitPrice = basket.getKey().getUnitPrice();
            char itemName = basket.getKey().getName();
            int noOfOccurrence = basket.getValue();

            itemTotal = unitPrice.multiply(new BigDecimal(noOfOccurrence));

            //filters the list of whole sale items to find the matching itemName
            List<SpecialPrice> filteredPriceRule = specialOffers.getSpecialOffers().stream().filter(e -> e.getStockItem().getName() == itemName && noOfOccurrence >= e.getNoOfItems()).collect(Collectors.toList());
            itemTotal = applySpecialPrice(filteredPriceRule, itemTotal, noOfOccurrence, unitPrice);


            calcTotal = calcTotal.add(itemTotal);
        }
        this.total = calcTotal;  //returns a big decimal type
    }

    private BigDecimal applySpecialPrice(List<SpecialPrice> filteredPriceRule, BigDecimal itemTotal, int noOfOccurrence, BigDecimal unitPrice){
        if(filteredPriceRule.size() == 1) {
            //item is eligible for discount if the noOfOccurrence is greater or equal to nofItems in wholesale offer
            SpecialPrice specialPrice = filteredPriceRule.stream().findFirst().get();
            BigDecimal discountPrice = specialPrice.getDiscountPrice();
            int noOfItemsOnSale = specialPrice.getNoOfItems();

            if(noOfItemsOnSale == noOfOccurrence) {
                itemTotal = discountPrice;  //returns price if noOfItem matches wholesale quantity
            } else{
                BigDecimal priceDifference = unitPrice.multiply(new BigDecimal(noOfItemsOnSale)).subtract(discountPrice);  // the difference between original and wholesale price
                int itemReduced = noOfOccurrence / noOfItemsOnSale;  //reduced item deducted from wholesale quantity

                itemTotal = itemTotal.subtract(priceDifference.multiply(new BigDecimal(itemReduced)));    //subtracts the difference from the total price of an item
            }
        }
        return  itemTotal;
    }


}
