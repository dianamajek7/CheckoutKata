package Wholesale;

import java.util.List;
import java.util.stream.Collectors;

public class WholesaleUtil {

    public static boolean checkSpecialOffersForDuplicates(List<SpecialPrice> specialPrice, char itemName, int noOfItems) {
        List<SpecialPrice> filtered = specialPrice.stream().filter(sp -> sp.getStockItem().getName() == itemName && sp.getNoOfItems() == noOfItems).collect(Collectors.toList());
        return filtered.size() > 0;
    }
}
