package util;

import Stock.StockItems;
import Wholesale.SpecialOffers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static util.Constants.ITEMS;
import static util.Constants.SPECIALPRICES;

public class Utility {
    public static <T> List<T> filter(Set<T> list, Predicate<T> test){
        List<T> filtered = new ArrayList<>();
        for(T t : list) {
            if(test.test(t)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> test){  //an overloading method taking a different parameter type but returns same value
        List<T> filtered = new ArrayList<>();
        for(T t : list) {
            if(test.test(t)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public static void initialise(StockItems stockItems, SpecialOffers specialOffers) {
        SkuUtil skuUtil = new SkuUtil();
        List<String> items = skuUtil.readInputFromResource(ITEMS);
        List<String>specialPrices = skuUtil.readInputFromResource(SPECIALPRICES);
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
    }
}
