package Wholesale;

import Stock.Product;
import Stock.StockItems;
import util.SkuUtil;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WholesaleUtil {
    private static final Logger LOGGER = Logger.getLogger( WholesaleUtil.class.getName() );
    SkuUtil skuUtil;
    StockItems stockItems;

    public void loadSpecialOffers(String fileName, SpecialOffers specialOffers) {
        List<String> specialPrices = skuUtil.readInputFromResource(fileName);
        specialPrices.forEach(sp -> {
            char itemName = sp.split(" ")[0].charAt(0); //each input format i.e. (A 2 for 130)

            //filter the list of stocks to get the stock price with the unique the item name
            Set<Product> filtered = stockItems.getProducts().stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());

            if(filtered.size() == 1) {
                float unitPrice = filtered.stream().findFirst().get().getUnitPrice();

                int noOfItems = Integer.parseInt(sp.split(" ")[1]);
                float discountPrice = Float.parseFloat(sp.split(" ")[3]);
                SpecialPrice specialPrice = new SpecialPrice(new Product(itemName, unitPrice), noOfItems, discountPrice);

                specialOffers.addSpecialPrice(specialPrice);
            }

        });

    }

    public void addSpecialOffers(char itemName, int noOfItems, float discountPrice, SpecialOffers specialOffers) {
        //filter the list of stocks to get the stock price with the unique the item name
        Set<Product> filtered = stockItems.getProducts().stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());

        if(filtered.size() == 1) {
            SpecialPrice specialPrice = new SpecialPrice(filtered.stream().findFirst().get(), noOfItems, discountPrice);
            specialOffers.addSpecialPrice(specialPrice);
        }

        String newOffer = itemName + " " + noOfItems + " for " + discountPrice; //to keep file updated
        skuUtil.writeToFile("/tests/resources/SpecialPrices", newOffer);
    }

}
