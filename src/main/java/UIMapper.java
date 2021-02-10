import Shopping.Basket;
import Shopping.Checkout;
import Stock.StockItems;
import Wholesale.SpecialOffers;
import util.SkuUtil;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static util.Constants.*;

public class UIMapper {
    private static final Logger LOGGER = Logger.getLogger( SpecialOffers.class.getName() );

    static SkuUtil skuUtil = new SkuUtil();

    public static void loadBasket(Basket basket, Checkout checkout){

        System.out.println("\nBelow are the list of available Items...");

        OutputFileContent(ITEMS);

        System.out.println("\nAdd items to your basket i.e- AAAB: " );
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        //validates userInput and returns appropriate error, if error occurs in routing it is passed down to the invoker class
        if(!isNull(userInput)) {

            String errorMessage = basket.addItemToBasket(userInput);

            if(!isNull(errorMessage))
                System.out.println(errorMessage);

            checkout.calculateTotal(basket.getShoppingBasket());

            float total = checkout.getTotal();
            System.out.println("Receipt.......");
            basket.getItemsTotal().forEach((product, itemTotal) -> {
                System.out.print("Item: "+ product.getName());
                System.out.print(" Unit Price: "+ product.getUnitPrice());
                System.out.println("Item Total: "+ itemTotal);
            });

            System.out.println("Total price: "+ total);

        }else{
            LOGGER.log(Level.SEVERE, NULLFOUND);
            System.out.println(NULLFOUND);
        }
    }

    public static void modifyStocks(StockItems stockItems){     //performs appropriate actions available for stocks menu option
        System.out.println("\nCurrent Stock...");

        OutputFileContent(ITEMS);

        System.out.println("\nEnter 1 to add an Item...");
        System.out.println("Enter 2 to remove an Item...");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        String errorMessage = UIValidator.isNumeric(userInput, LOGGER);
        if(isNull(errorMessage)) {
            switch (Integer.parseInt(userInput)){
                case 1:
                   // addItemToStock
                    System.out.println("\nAdd item to Stock - Name, UnitPrice i.e - A 4: " );
                    scanner = new Scanner(System.in);
                    userInput = scanner.nextLine();
                    addItemToStock(userInput, stockItems);
                    break;
                case 2:
                    // removeFromStock
                    System.out.println("\nRemove item from Stock - Name i.e - A: " );
                    scanner = new Scanner(System.in);
                    userInput = scanner.nextLine();
                    removeItemFromStock(userInput, stockItems);
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }else{
            System.out.println(errorMessage);
        }

    }

    public static void modifySpecialPrice(StockItems stockItems, SpecialOffers specialOffers) { //performs appropriate actions available for stocks menu option
        System.out.println("\nCurrent Stock...");
        OutputFileContent(ITEMS);
        System.out.println("\nCurrent Whole Sale Prices...");
        OutputFileContent(SPECIALPRICES);

        System.out.println("\nEnter 1 to add a Price Rule...");
        System.out.println("Enter 2 to remove a Price Rule...");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        String errorMessage = UIValidator.isNumeric(userInput, LOGGER);
        if(isNull(errorMessage)) {
            switch (Integer.parseInt(userInput)){
                case 1:
                    // addSpecialRule
                    System.out.println("\nAdd Pricing rule to Whole Sale - Name, No of Items, discount price  i.e - A 4 50: " );
                    scanner = new Scanner(System.in);
                    userInput = scanner.nextLine();
                    addISpecialRule(userInput, stockItems, specialOffers);
                    break;
                case 2:
                    // removeItemFromWholeSales
                    System.out.println("\nRemove item from Stock - Name, No of Items i.e - A 3: " );
                    scanner = new Scanner(System.in);
                    userInput = scanner.nextLine();
                    removeItemFromWholeSale(userInput, specialOffers);
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }else{
            System.out.println(errorMessage);
        }
    }

    private static void addISpecialRule(String userInput, StockItems stockItems, SpecialOffers specialOffers) {
        if(!isNull(userInput)) {
            if(userInput.split(" ").length == 3) {
                String str_nofItems = userInput.split(" ")[1];
                String str_discountPrice = userInput.split(" ")[2];

                String errorMessage = UIValidator.isNumeric(str_nofItems, LOGGER);
                String errorMessage2 = UIValidator.isNumeric(str_discountPrice, LOGGER);
                if(isNull(errorMessage) && isNull(errorMessage2)){

                    char item = userInput.split(" ")[0].toUpperCase().charAt(0);
                    int nofItems = Integer.parseInt(str_nofItems);
                    int discountPrice = Integer.parseInt(str_discountPrice);

                    //adds pricing rule to whole sale list, retrieves an error if occurred during routing
                    errorMessage = specialOffers.addSpecialOffer(item, nofItems, discountPrice, stockItems.getProducts(), SPECIALPRICE_FILE);
                    if(isNull(errorMessage)){
                        System.out.println("Success Added, current WholeSale...");
                        OutputFileContent(SPECIALPRICES); //reloads currentStock
                        System.out.println(userInput.toUpperCase());
                    }else {
                        System.out.println(errorMessage);
                    }

                } else {
                    LOGGER.log(Level.SEVERE, PRICE_NOTUMBERIC);
                    System.out.println(PRICE_NOTUMBERIC);
                }
            } else {
                LOGGER.log(Level.SEVERE, ALLDETAILS_REQUIRED);
                System.out.println(ALLDETAILS_REQUIRED);
            }


        }else{
            LOGGER.log(Level.SEVERE, NULLFOUND);
            System.out.println(NULLFOUND);
        }

    }

    private static void removeItemFromWholeSale(String userInput, SpecialOffers specialOffers) {

        if(!isNull(userInput)) {
            if(userInput.split(" ").length == 2){
                char item = userInput.toUpperCase().charAt(0);

                String str_noOfItems = userInput.split(" ")[1];
                String errorMessage = UIValidator.isNumeric(str_noOfItems, LOGGER);
                if(isNull(errorMessage)){

                    int noOfItems = Integer.parseInt(str_noOfItems);
                    errorMessage = specialOffers.removeSpecialOffer(item, noOfItems, SPECIALPRICE_FILE);    //removes rule from whole sale
                    if(isNull(errorMessage)){
                        System.out.println("\nSuccess Deleted, Current Stock...");

                    }else {
                        System.out.println(errorMessage);
                    }

                } else {
                    LOGGER.log(Level.SEVERE, PRICE_NOTUMBERIC);
                    System.out.println(PRICE_NOTUMBERIC);
                }

            } else {
                LOGGER.log(Level.SEVERE, ALLDETAILS_REQUIRED);
                System.out.println(ALLDETAILS_REQUIRED);
            }


        }else{
            LOGGER.log(Level.SEVERE, NULLFOUND);
            System.out.println(NULLFOUND);
        }
    }


    private static void addItemToStock(String userInput, StockItems stockItems) {

        if(!isNull(userInput)) {
            if(userInput.split(" ").length == 2){
                String strUnitPrice = userInput.split(" ")[1];
                String errorMessage = UIValidator.isNumeric(strUnitPrice, LOGGER);
                if(isNull(errorMessage)){
                    char item = userInput.split(" ")[0].toUpperCase().charAt(0);
                    int unitPrice = Integer.parseInt(strUnitPrice);
                    errorMessage = stockItems.addStockItem(item, unitPrice, ITEMS_FILE);    //add Item to the list of existing stocks
                    if(isNull(errorMessage)){
                        System.out.println("Success Added, current Stock...");
                        OutputFileContent(ITEMS); //reloads currentStock
                        System.out.println(userInput.toUpperCase());
                    }else {
                        System.out.println(errorMessage);
                    }


                } else {
                    LOGGER.log(Level.SEVERE, PRICE_NOTUMBERIC);
                    System.out.println(PRICE_NOTUMBERIC);
                }
            } else {
                LOGGER.log(Level.SEVERE, ALLDETAILS_REQUIRED);
                System.out.println(ALLDETAILS_REQUIRED);
            }


        }else{
            LOGGER.log(Level.SEVERE, NULLFOUND);
            System.out.println(NULLFOUND);
        }

    }

    private static void removeItemFromStock(String userInput, StockItems stockItems) {
        if(!isNull(userInput)) {

            char item = userInput.toUpperCase().charAt(0);
            String errorMessage = stockItems.removeStockItem(item, ITEMS_FILE); //remove item from existing list of stocks
            if(isNull(errorMessage)){
                System.out.println("\nSuccess Deleted, Current Stock...");

            }else {
                System.out.println(errorMessage);
            }


        }else{
            LOGGER.log(Level.SEVERE, NULLFOUND);
            System.out.println(NULLFOUND);
        }
    }

    private static void OutputFileContent(String fileName){
        List<String> inputLineList = skuUtil.readInputFromResource(fileName);
        inputLineList.forEach(System.out::println);  //prints out each line of input
    }

}