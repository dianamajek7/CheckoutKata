package com.challenge.checkoutKata.wholesale;

import com.challenge.checkoutKata.stock.Product;
import com.challenge.checkoutKata.util.ExceptionHandling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.challenge.checkoutKata.util.Constants.*;
import static com.challenge.checkoutKata.util.Utility.deleteALineFromFile;
import static com.challenge.checkoutKata.util.Utility.writeToFile;
import static java.util.Objects.isNull;

public class SpecialOffers {
    private static final Logger LOGGER = Logger.getLogger( SpecialOffers.class.getName() );
    private final List<SpecialPrice> specialOffers;

    public SpecialOffers() {
        this.specialOffers = new ArrayList<>();
    }

    public List<SpecialPrice> getSpecialOffers() {
        return specialOffers;
    }

    public void addSpecialPrice(SpecialPrice specialPrice) {
        if(isNull(specialPrice)){
            LOGGER.log(Level.SEVERE, NULLFOUND);
            return;
        }
        this.specialOffers.add(specialPrice);
    }

    public void loadSpecialOffers(List<String> specialPrices, Set<Product> stockItems) {

        if(isNull(specialPrices) || isNull(stockItems)) {
            LOGGER.log(Level.WARNING, EMPTY_SALES);
            return;
        }

        specialPrices.forEach(sp -> {
            char itemName = sp.split(" ")[0].charAt(0); //each input format i.e. (A 2 for 130)

            //filter the list of stocks to get the stock price with the unique item name
            Set<Product> filtered = stockItems.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());

            if(filtered.size() == 1) {
                BigDecimal unitPrice = filtered.stream().findFirst().get().getUnitPrice();

                int noOfItems = Integer.parseInt(sp.split(" ")[1]);
                BigDecimal discountPrice = new BigDecimal(sp.split(" ")[3]);
                SpecialPrice specialPrice = new SpecialPrice(new Product(itemName, unitPrice), noOfItems, discountPrice);
                this.addSpecialPrice(specialPrice);
            }

        });
    }

    public void addSpecialOffer(char itemName, int noOfItems, BigDecimal discountPrice, Set<Product> stockItems, String fileName) throws ExceptionHandling {
        //filter the list of pricingRules to validate if rule already exists
        List<SpecialPrice> priceRuleFiltered = this.getSpecialOffers().stream().filter(e->e.getStockItem().getName() == itemName && e.getNoOfItems() == noOfItems).collect(Collectors.toList());

        if(priceRuleFiltered.size() > 0) {
            String msg = PRICINGRULE_EXIST;
            LOGGER.log(Level.SEVERE, "Caught Exception: " + msg);
            throw new ExceptionHandling(msg);
        }else{
            //filter the list of stocks to get the stock price with the unique itemName
            Set<Product> filtered = stockItems.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());
            if(filtered.size() == 1) {
                SpecialPrice specialPrice = new SpecialPrice(filtered.stream().findFirst().get(), noOfItems, discountPrice);
                this.addSpecialPrice(specialPrice);
                String newOffer = itemName + " " + noOfItems + " for " + discountPrice; //to keep file updated
                writeToFile(fileName, newOffer);
            } else {
                String msg = ITEM_NOTFOND + ": " + itemName;
                LOGGER.log(Level.SEVERE, "Caught Exception: " + msg);
                throw new ExceptionHandling(msg);
            }
        }
    }

    public void removeSpecialOffer(char item, int noOfItems, String fileName) throws ExceptionHandling {
        //if a duplicate was found then the pricingRule to delete exist
        List<SpecialPrice> priceRuleFiltered = this.getSpecialOffers().stream().filter(e->e.getStockItem().getName() == item && e.getNoOfItems() == noOfItems).collect(Collectors.toList());

        if(priceRuleFiltered.size() == 0) {
            String msg = PRICINGRULE_NOTPRESENT;
            LOGGER.log(Level.SEVERE, "Caught Exception: " + msg);
            throw new ExceptionHandling(msg);
        } else if (priceRuleFiltered.size() == 1) {

            this.specialOffers.removeIf(e -> e.getNoOfItems() == noOfItems && e.getStockItem().getName() == item);
            String line = item + " " + noOfItems + " for ";
            deleteALineFromFile(fileName, line);    //removes line containing the specific field from file to keep updated for a fresh reload
        }
    }

}
