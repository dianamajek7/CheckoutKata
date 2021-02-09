package Wholesale;

import Stock.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static Wholesale.WholesaleUtil.checkSpecialOffersForDuplicates;
import static java.util.Objects.isNull;
import static util.Constants.*;
import static util.SkuUtil.deleteALineFromFile;
import static util.SkuUtil.writeToFile;

public class SpecialOffers {
    private static final Logger LOGGER = Logger.getLogger( SpecialOffers.class.getName() );
    private final List<SpecialPrice> specialOffers;

    public SpecialOffers() {
        this.specialOffers = new ArrayList<>();
    }

    public  List<SpecialPrice> getSpecialOffers() {
        return specialOffers;
    }

    public void addSpecialPrice(SpecialPrice specialPrice) {
        if(isNull(specialPrice)){
            System.out.println(NULLFOUND);
            return;
        }
        this.specialOffers.add(specialPrice);
    }

    public void loadSpecialOffers(List<String> specialPrices, Set<Product> stockItems) {

        specialPrices.forEach(sp -> {
            char itemName = sp.split(" ")[0].charAt(0); //each input format i.e. (A 2 for 130)

            //filter the list of stocks to get the stock price with the unique the item name
            Set<Product> filtered = stockItems.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());

            if(filtered.size() == 1) {
                float unitPrice = filtered.stream().findFirst().get().getUnitPrice();

                int noOfItems = Integer.parseInt(sp.split(" ")[1]);
                float discountPrice = Float.parseFloat(sp.split(" ")[3]);
                SpecialPrice specialPrice = new SpecialPrice(new Product(itemName, unitPrice), noOfItems, discountPrice);

                this.addSpecialPrice(specialPrice);
            }

        });

    }
    public String addSpecialOffers(char itemName, int noOfItems, float discountPrice, Set<Product> stockItems, String writeFile) {

        //avoids duplicate using noOfItems and itemName as reference
        boolean duplicate = checkSpecialOffersForDuplicates(this.getSpecialOffers(), itemName, noOfItems);
        String msg = null;

        if(duplicate) {
            msg = PRICINGRULE_EXIST;
            LOGGER.log(Level.WARNING, msg);
        } else {
            //filter the list of stocks to get the stock price with the unique the item name
            Set<Product> filtered = stockItems.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());
            if(filtered.size() == 1) {
                SpecialPrice specialPrice = new SpecialPrice(filtered.stream().findFirst().get(), noOfItems, discountPrice);
                this.addSpecialPrice(specialPrice);
                String newOffer = itemName + " " + noOfItems + " for " + discountPrice; //to keep file updated
                writeToFile(writeFile, newOffer);
            } else {
                msg = ITEM_NOTFOND + " for " + itemName;
                LOGGER.log(Level.WARNING, msg);
            }
        }

        return msg;
    }

    public String removeSpecialOffer(char item, int noOfItems, String fileName) {
        //if a duplicate was found then the pricing rile to delete exist
        String msg = null;
        boolean duplicate = checkSpecialOffersForDuplicates(this.getSpecialOffers(), item, noOfItems);

        if(!duplicate) {
            msg = PRICINGRULE_NOTPRESENT;
            LOGGER.log(Level.WARNING, msg);
        } else{
            this.specialOffers.removeIf(e -> e.getNoOfItems() == noOfItems && e.getStockItem().getName() == item);
        }
        String line = item + " " + noOfItems + " for ";
        deleteALineFromFile(fileName, line);

        return msg;
    }

}
