package stock;

import util.Utility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static util.Constants.*;
import static util.SkuUtil.deleteALineFromFile;
import static util.SkuUtil.writeToFile;

public class StockItems {
    private static final Logger LOGGER = Logger.getLogger( StockItems.class.getName() );
    private final Set<Product> products;

    public StockItems() {
        this.products = new HashSet<>();
    }

    public Set<Product> getProducts() { return products; }

    public void addProduct(Product product) {

        if(isNull(product)){
            String msg = NULLFOUND;
            LOGGER.log(Level.WARNING, msg);
            return;
        }

        this.products.add(product);
    }

    public void loadStockItems(List<String> items) {
        //loads the existing inventory from file
        items.forEach(item -> {
            String itemName = item.split(" ")[0];   //each line in the file contains a space between the item and the unitPrice
            int unitPrice = Integer.parseInt(item.split(" ")[1]);
            Product product = new Product(itemName.charAt(0), unitPrice);

            this.addProduct(product);
        });
    }

    public String addStockItem(char itemName, int unitPrice, String writeFile) {

        //filter the list of stocks to validate if item already exists
        List<Product> productsFiltered = Utility.filter(this.getProducts(), e->e.getName() == itemName);
        String msg = null;

        if(productsFiltered.size() > 0) {
            msg = ITEM_EXIST;
            LOGGER.log(Level.SEVERE, msg);
        } else {
            Product product = new Product(itemName, unitPrice);
            this.addProduct(product);
            String newOffer = itemName + " " +  unitPrice; //to keep file updated
            writeToFile(writeFile, newOffer);
        }

        return msg;
    }


    public String removeStockItem(char item, String fileName) {
        //if a duplicate was found then the pricing rile to delete exist
        String msg = null;
        List<Product> productsFiltered = Utility.filter(this.getProducts(), e->e.getName() == item);

        if(productsFiltered.size() == 0) {
            msg = ITEM_NOTFOND;
            LOGGER.log(Level.SEVERE, msg);
        } else if (productsFiltered.size() == 1) {

            this.products.removeIf(e -> e.getName() == item);

            if(productsFiltered.size() == 1){
                String line = item + " " + productsFiltered.stream().findFirst().get().getUnitPrice();
                deleteALineFromFile(fileName, line);    //removes the last line from file that matches the itemName
            }

        }
        return msg;
    }
}
