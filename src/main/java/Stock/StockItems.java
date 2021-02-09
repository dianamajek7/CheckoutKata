package Stock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.Objects.isNull;
import static util.Constants.NULLFOUND;

public class StockItems {
    private static final Logger LOGGER = Logger.getLogger( StockItems.class.getName() );
    private final Set<Product> products;

    public StockItems() {
        this.products = new HashSet<>();
    }

    public Set<Product> getProducts() { return products; }

    public String addProduct(Product product) {

        if(isNull(product)){
            String msg = NULLFOUND;
            LOGGER.log(Level.WARNING, msg);
            return msg;
        }

        this.products.add(product);
        return null;
    }

    public void loadStockItems(List<String> items) {
        //loads the existing inventory from file

        items.forEach(item -> {
            String itemName = item.split(" ")[0];   //each line in the file contains a space between the item and the unitPrice
            float unitPrice = Float.parseFloat(item.split(" ")[1]);
            Product product = new Product(itemName.charAt(0), unitPrice);

            this.addProduct(product);
        });
    }


//    public static Set<Product> removeStockItem(char item) {
//        stocks.removeIf(e -> e.getName() == item);
//        stocks.forEach(i -> {
//            System.out.println(i.getName());
//            System.out.println(i.getUnitPrice());
//
//        });
//        return stocks;
//    }
}
