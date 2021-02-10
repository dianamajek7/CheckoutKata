package Shopping;

import Stock.Product;
import Stock.StockItems;
import util.Utility;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static util.Constants.ITEM_NOTFOND;
import static util.Constants.NULLFOUND;

public class Basket {
    private static final Logger LOGGER = Logger.getLogger( Basket.class.getName() );

    private final Map<Product, Integer> shoppingBasket;
    StockItems stockItems;

    public Basket() {
        this.stockItems = new StockItems();
        BasketUtil.initialiseStocks(stockItems);

        this.shoppingBasket = new HashMap<>();
    }

    public Map<Product, Integer> getShoppingBasket() { return shoppingBasket; }

    public String addItemToBasket(String inputItems) {
        String msg = null;
        if (isNull(inputItems)) {
            msg = NULLFOUND;
            LOGGER.log(Level.SEVERE, msg);
            return msg;
        }

        Map<Character, Integer> items = extractItems(inputItems);   //extract the product and total no of occurrence
        for(Map.Entry<Character, Integer> entry : items.entrySet()) {
            Character key = entry.getKey();
            Integer noOfOccurrence = entry.getValue();

            //gets the product from the list of available stocks
            List<Product> productsFiltered = Utility.filter(stockItems.getProducts(), e->e.getName() == key);
            if(productsFiltered.size() == 1) {
                Product product = productsFiltered.stream().findFirst().get();
                shoppingBasket.put(product, noOfOccurrence);
            } else if (productsFiltered.size() == 0) {
                msg = ITEM_NOTFOND + ": "+ key;
                LOGGER.log(Level.SEVERE, msg);
            }
        }

        return msg;
    }

    private Map<Character, Integer> extractItems(String inputItems) {
        char[] inputArray = inputItems.toCharArray();
        List<Character> itemList = new ArrayList<>();
        Map<Character, Integer> items = new HashMap<>();

        for(char i : inputArray) {
            itemList.add(i);   //store each item into a list
        }
        itemList.forEach(item-> {

            int occur = Collections.frequency(itemList, item); //records the number of occurrence of each item
            if(!items.containsKey(item)) {
                items.put(item, occur);
            }
        });

        return items;
    }

}