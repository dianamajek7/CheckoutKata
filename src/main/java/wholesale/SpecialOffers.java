package wholesale;

import stock.Product;
import util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public List<SpecialPrice> getSpecialOffers() {
        return specialOffers;
    }

    public void addSpecialPrice(SpecialPrice specialPrice) {
        if(isNull(specialPrice)){
            String msg = NULLFOUND;
            LOGGER.log(Level.WARNING, msg);
            return;
        }
        this.specialOffers.add(specialPrice);
    }

    public void loadSpecialOffers(List<String> specialPrices, Set<Product> stockItems) {

        specialPrices.forEach(sp -> {
            char itemName = sp.split(" ")[0].charAt(0); //each input format i.e. (A 2 for 130)

            //filter the list of stocks to get the stock price with the unique item name
            Set<Product> filtered = stockItems.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());

            if(filtered.size() == 1) {
                int unitPrice = filtered.stream().findFirst().get().getUnitPrice();

                int noOfItems = Integer.parseInt(sp.split(" ")[1]);
                int discountPrice = Integer.parseInt(sp.split(" ")[3]);
                SpecialPrice specialPrice = new SpecialPrice(new Product(itemName, unitPrice), noOfItems, discountPrice);

                this.addSpecialPrice(specialPrice);
            }

        });

    }

    public String addSpecialOffer(char itemName, int noOfItems, int discountPrice, Set<Product> stockItems, String fileName) {
        //filter the list of pricingRules to validate if rule already exists
        List<SpecialPrice> priceRuleFiltered = Utility.filter(this.getSpecialOffers(), e->e.getStockItem().getName() == itemName && e.getNoOfItems() == noOfItems);

        String msg = null;
        if(priceRuleFiltered.size() > 0) {
            msg = PRICINGRULE_EXIST;
            LOGGER.log(Level.SEVERE, msg);
        }else{
            //filter the list of stocks to get the stock price with the unique the itemName
            Set<Product> filtered = stockItems.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());
            if(filtered.size() == 1) {
                SpecialPrice specialPrice = new SpecialPrice(filtered.stream().findFirst().get(), noOfItems, discountPrice);
                this.addSpecialPrice(specialPrice);
                String newOffer = itemName + " " + noOfItems + " for " + discountPrice; //to keep file updated
                writeToFile(fileName, newOffer);
            } else {
                msg = ITEM_NOTFOND + ": " + itemName;
                LOGGER.log(Level.SEVERE, msg);
            }
        }

        return msg;
    }

    public String removeSpecialOffer(char item, int noOfItems, String fileName) {
        //if a duplicate was found then the pricingRule to delete exist
        String msg = null;
        List<SpecialPrice> priceRuleFiltered = Utility.filter(this.getSpecialOffers(), e->e.getStockItem().getName() == item && e.getNoOfItems() == noOfItems);

        if(priceRuleFiltered.size() == 0) {
            msg = PRICINGRULE_NOTPRESENT;
            LOGGER.log(Level.SEVERE, msg);
        } else if (priceRuleFiltered.size() == 1) {

            this.specialOffers.removeIf(e -> e.getNoOfItems() == noOfItems && e.getStockItem().getName() == item);
            String line = item + " " + noOfItems + " for ";
            deleteALineFromFile(fileName, line);    //removes line containing the specific field from file to keep updated for a fresh reload
        }

        return msg;
    }

}
