package Wholesale;

import util.SkuUtil;

import java.util.List;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class SpecialOffers {
    private static final Logger LOGGER = Logger.getLogger( SpecialOffers.class.getName() );
    private  List<SpecialPrice> specialOffers;

    public  List<SpecialPrice> getSpecialOffers() {
        return specialOffers;
    }

    public void addSpecialPrice(SpecialPrice specialPrice) {
        if(isNull(specialPrice)){
            System.out.println("Special Price is null");
            return;
        }

        this.specialOffers.add(specialPrice);
    }

//    public static Set<Stock.StockItem> removeSpecialOffer(String item, int noOfItems) {
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
