package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Utility {
    public static <T> List<T> filter(Set<T> list, Predicate<T> test){
        List<T> filtered = new ArrayList<>();
        for(T t : list) {
            if(test.test(t)) {
                filtered.add(t);
            }
        }
        return filtered;
    }//List<Stock.StockItem> filtered = Utility.filter(stocks, e->e.getName() == itemName);
}
