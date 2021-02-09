package Stock;

import util.SkuUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class StockUtil {
    SkuUtil skuUtil;
    private static final Logger LOGGER = Logger.getLogger( StockUtil.class.getName() );

    public void loadStockItems(String fileName, StockItems stockItems) {
        //loads the existing inventory from file
        List<String> items = skuUtil.readInputFromResource(fileName);
        items.forEach(item -> {
            String itemName = item.split(" ")[0];   //each line in the file contains a space between the item and the unitPrice
            float unitPrice = Float.parseFloat(item.split(" ")[1]);
            Product product = new Product(itemName.charAt(0), unitPrice);

            stockItems.addProduct(product);
        });
    }

//    public Set<Product> loadStockItems(String fileName) {
//        Set<Product> products = new HashSet<>();
//        //loads the existing inventory from file
//        List<String> items = skuUtil.readInputFromResource(fileName);
//        items.forEach(item -> {
//            String itemName = item.split(" ")[0];   //each line in the file contains a space between the item and the unitPrice
//            float unitPrice = Float.parseFloat(item.split(" ")[1]);
//            Product product = new Product(itemName.charAt(0), unitPrice);
//
//            products.add(product);
//        });
//        return products;
//    }
}
