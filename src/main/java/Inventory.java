import util.SkuUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Inventory {
    private static Set<StockItem> stocks = new HashSet<>();
    private static List<SpecialPrice> specialOffers = new ArrayList<>();
    private static SkuUtil skuUtil = new SkuUtil();

    public static Set<StockItem> loadStockItem(String fileName) {
        //loads the existing inventory
        List<String> items = skuUtil.getInputFromResource(fileName);
        items.forEach(item -> {
            String itemName = item.split(" ")[0];   //each line in the file contains a space between the item and the unitPrice
            float unitPrice = Float.parseFloat(item.split(" ")[1]);
            StockItem stockItem = new StockItem(itemName.charAt(0), unitPrice);

            stocks.add(stockItem);
        });
        return stocks;
    }

    public static List<SpecialPrice> loadSpecialOffers(String fileName) {
        List<String> specialPrices = skuUtil.getInputFromResource(fileName);
        specialPrices.forEach(sp -> {
            char itemName = sp.split(" ")[0].charAt(0);

            //filter the list of stocks to get the stock price with the unique the item name
            Set<StockItem> filtered = stocks.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());

            if(filtered.size() == 1) {
                float unitPrice = filtered.stream().findFirst().get().getUnitPrice();

                int noOfItems = Integer.parseInt(sp.split(" ")[1]);
                float discountPrice = Float.parseFloat(sp.split(" ")[3]);
                SpecialPrice specialPrice = new SpecialPrice(new StockItem(itemName, unitPrice), noOfItems, discountPrice);

                specialOffers.add(specialPrice);
            }

        });
        return specialOffers;
    }

    public static List<SpecialPrice> addSpecialOffers(char itemName, int noOfItems, float discountPrice) {
        //filter the list of stocks to get the stock price with the unique the item name
        Set<StockItem> filtered = stocks.stream().filter(item -> item.getName() == itemName).collect(Collectors.toSet());

        if(filtered.size() == 1) {
            SpecialPrice specialPrice = new SpecialPrice(filtered.stream().findFirst().get(), noOfItems, discountPrice);
            specialOffers.add(specialPrice);
        }

        String newOffer = itemName + " " + noOfItems + " for " + discountPrice;
        skuUtil.writeToFile("SpecialPrices", newOffer);


        return  specialOffers;
    }

    public static Set<StockItem> removeStockItem(char item) {
        stocks.removeIf(e -> e.getName() == item);
        stocks.forEach(i -> {
            System.out.println(i.getName());
            System.out.println(i.getUnitPrice());

        });
        return stocks;
    }

//    public static Set<StockItem> removeSpecialOffer(String item, int noOfItems) {
//        specialOffers.removeIf(e -> {
//            e.getNoOfItems() == noOfItems
//
//        }  );
//        stocks.forEach(i -> {
//            System.out.println(i.getName());
//            System.out.println(i.getUnitPrice());
//
//        });
//        return stocks;
//    }








}
