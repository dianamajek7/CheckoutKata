package Stock;

import Wholesale.WholesaleUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class StockItems {
    private static final Logger LOGGER = Logger.getLogger( StockItems.class.getName() );
    private Set<Product> products;

    public Set<Product> getProducts() { return products; }

    public void addProduct(Product product) {
        if(isNull(product)){
            System.out.println("Product is null");
            return;
        }

        this.products.add(product);
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
