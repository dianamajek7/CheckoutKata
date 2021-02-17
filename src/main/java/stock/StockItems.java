package stock;

import util.ExceptionHandling;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static util.Constants.*;
import static util.Utility.deleteALineFromFile;
import static util.Utility.writeToFile;

public class StockItems {
    private static final Logger LOGGER = Logger.getLogger( StockItems.class.getName() );
    private final Set<Product> products;

    public StockItems() {
        this.products = new HashSet<>();
    }

    public Set<Product> getProducts() { return products; }

    public void addProduct(Product product)  {

        if(isNull(product)){
            LOGGER.log(Level.SEVERE, NULLFOUND);
            return;
        }

        this.products.add(product);
    }

    public void loadStockItems(List<String> items) {  //internal use
        //loads the existing inventory from file
        if (isNull(items)) {
            LOGGER.log(Level.WARNING, EMPTY_STOCK);
            return;
        }
        items.forEach(item -> {
            String itemName = item.split(" ")[0];   //each line in the file contains a space between the item and the unitPrice
            BigDecimal unitPrice = new BigDecimal(item.split(" ")[1]);
            Product product = new Product(itemName.charAt(0), unitPrice);
            this.addProduct(product);
        });
    }

    public void addStockItem(char itemName, BigDecimal unitPrice, String writeFile) throws ExceptionHandling {

        //filter the list of stocks to validate if item already exists
        List<Product> productsFiltered = this.getProducts().stream().filter( e->e.getName() == itemName).collect(Collectors.toList());

        if(productsFiltered.size() > 0) {
            String msg = ITEM_EXIST;
            LOGGER.log(Level.SEVERE, "Caught Exception: " + msg);
            throw new ExceptionHandling(msg);
        } else {
            Product product = new Product(itemName, unitPrice);
            this.addProduct(product);
            String newOffer = itemName + " " +  unitPrice; //to keep file updated
            writeToFile(writeFile, newOffer);
        }
    }


    public void removeStockItem(char item, String fileName) throws ExceptionHandling {
        //if a duplicate was found then the pricing rile to delete exist
        List<Product> productsFiltered = this.getProducts().stream().filter( e->e.getName() == item).collect(Collectors.toList());

        if(productsFiltered.size() == 0) {
            String msg = ITEM_NOTFOND;
            LOGGER.log(Level.SEVERE, "Caught Exception: " + msg);
            throw new ExceptionHandling(msg);
        } else if (productsFiltered.size() == 1) {

            this.products.removeIf(e -> e.getName() == item);

            String line = item + " " + productsFiltered.stream().findFirst().get().getUnitPrice();
            deleteALineFromFile(fileName, line);    //removes the last line from file that matches the itemName

        }
    }
}
