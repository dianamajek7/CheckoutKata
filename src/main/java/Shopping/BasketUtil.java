package Shopping;

import Stock.StockItems;
import Wholesale.SpecialOffers;
import util.SkuUtil;

import java.util.List;

import static util.Constants.ITEMS;
import static util.Constants.SPECIALPRICES;

public class BasketUtil {
    public static void initialiseStocks(StockItems stockItems) {
        SkuUtil skuUtil = new SkuUtil();
        List<String> items = skuUtil.readInputFromResource(ITEMS);
        stockItems.loadStockItems(items);
    }

    public static void initialiseWholeSale(StockItems stockItems, SpecialOffers specialOffers) {
        SkuUtil skuUtil = new SkuUtil();
        List<String> items = skuUtil.readInputFromResource(ITEMS);
        List<String>specialPrices = skuUtil.readInputFromResource(SPECIALPRICES);
        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
    }


}
