import shopping.Basket;
import shopping.Checkout;
import stock.Product;
import stock.StockItems;
import util.ExceptionHandling;
import util.Utility;
import wholesale.SpecialOffers;
import wholesale.SpecialPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static util.Constants.*;

public class UIMapper {
    private static final Logger LOGGER = Logger.getLogger( UIMapper.class.getName() );

    static Utility utility = new Utility();

    public static void shopping(Basket basket, Checkout checkout, SpecialOffers specialOffers) {

        //validates userInput and returns appropriate error, if error occurs in routing it is passed down to the invoker class
        try {
            System.out.println("\nBelow are the list of available Items...");

            OutputFileContent(ITEMS);
            System.out.println("\nAdd items to your basket i.e- AAAB: " );
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            scanner.close();
            UIValidator.validateIsEmpty(userInput);
            UIValidator.validateInputFormat(userInput, REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED);

            basket.addItemToBasket(userInput);
            checkout.calculateTotal(basket.getShoppingBasket());
            BigDecimal total = checkout.getTotal();

            System.out.println("\nReceipt.......");
            //prints out the each product price, sale and accumulated total based on no of occurrence
            basket.getItemsTotal().forEach((product, itemTotal) -> {

                Map<Product, Integer> filter =  basket.getShoppingBasket().entrySet().stream()
                        .filter(x -> product.getName() == x.getKey().getName())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                if(filter.size() == 1){
                    int noOfItemOccurrence = filter.entrySet().stream().findFirst().get().getValue();

                    System.out.print("Item: "+ product.getName() + " | ");
                    System.out.print("No: "+ noOfItemOccurrence + " | ");
                    System.out.print("Unit Price: "+ product.getUnitPrice() + " | ");
                    System.out.println("Accumulated : "+ itemTotal);
                    //a check for if discount was applied
                    List<SpecialPrice> sp = specialOffers.getSpecialOffers().stream().filter(e->
                            e.getStockItem().getName() == product.getName() && noOfItemOccurrence >= e.getNoOfItems()).collect(Collectors.toList());
                    if(sp.size() == 1)
                        System.out.println("Discount Applied");
                }

            });

            System.out.println("Total price: "+ total);

        } catch (ExceptionHandling e){ //Catches an error if occurred during routing
            System.out.println(e.getMessage());
        }
    }

    public static void modifyStocks(StockItems stockItems){     //performs appropriate actions available for stocks menu option

        try{
            System.out.println("\nCurrent Stock...");

            OutputFileContent(ITEMS);

            System.out.println("\nEnter 1 to add an Item...");
            System.out.println("Enter 2 to remove an Item...");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            UIValidator.validateIsNumeric(userInput);
            switch (Integer.parseInt(userInput)){
                case 1:
                    // addItemToStock
                    System.out.println("\nAdd item to Stock - Name, UnitPrice i.e - A 4: " );
                    userInput = scanner.nextLine();
                    addItemToStock(userInput, stockItems);
                    break;
                case 2:
                    // removeFromStock
                    System.out.println("\nRemove item from Stock - Name i.e - A: " );
                    userInput = scanner.nextLine();
                    removeItemFromStock(userInput, stockItems);
                    break;
                default:
                    System.out.println(INVALID_OPTION);
                    break;
            }
            scanner.close();
        } catch (ExceptionHandling e) {
            System.out.println(e.getMessage());
        }
    }

    public static void modifySpecialPrice(StockItems stockItems, SpecialOffers specialOffers) { //performs appropriate actions available for stocks menu option

        try{
            System.out.println("\nCurrent Stock...");
            OutputFileContent(ITEMS);
            System.out.println("\nCurrent Whole Sale Prices...");
            OutputFileContent(SPECIALPRICES);

            System.out.println("\nEnter 1 to add a Price Rule...");
            System.out.println("Enter 2 to remove a Price Rule...");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            UIValidator.validateIsNumeric(userInput);
            switch (Integer.parseInt(userInput)){
                case 1:
                    // addSpecialRule
                    System.out.println("\nAdd Pricing rule to Whole Sale - Name, No of Items, discount price  i.e - A 4 50: " );
                    userInput = scanner.nextLine();
                    addISpecialRule(userInput, stockItems, specialOffers);
                    break;
                case 2:
                    // removeItemFromWholeSales
                    System.out.println("\nRemove item from Stock - Name, No of Items i.e - A 3: " );
                    userInput = scanner.nextLine();
                    removeItemFromWholeSale(userInput, specialOffers);
                    break;
                default:
                    System.out.println(INVALID_OPTION);
                    break;
            }
            scanner.close();
        } catch (ExceptionHandling e) {
            System.out.println(e.getMessage());
        }

    }

    private static void addItemToStock(String userInput, StockItems stockItems) {

        try{
            UIValidator.validateIsEmpty(userInput);
            UIValidator.validateInputFormat(userInput, REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED);
            if(userInput.split(" ").length == 2){
                String strUnitPrice = userInput.split(" ")[1];
                UIValidator.validateIsNumeric(strUnitPrice);
                char item = userInput.split(" ")[0].toUpperCase().charAt(0);
                BigDecimal unitPrice = new BigDecimal(strUnitPrice);

                if(!unitPrice.equals(BigDecimal.ZERO)){
                    stockItems.addStockItem(item, unitPrice, ITEMS_FILE);    //add Item to the list of existing stocks
                    System.out.println("Success Added, current Stock...");
                    OutputFileContent(ITEMS); //reloads currentStock
                    System.out.println(userInput.toUpperCase());
                } else {
                    LOGGER.log(Level.SEVERE, PRICE_ZERO);
                    System.out.println(PRICE_ZERO);
                }
            }
        } catch (ExceptionHandling e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeItemFromStock(String userInput, StockItems stockItems) {
        try {
            char item = userInput.toUpperCase().charAt(0);
            stockItems.removeStockItem(item, ITEMS_FILE); //remove item from existing list of stocks
            System.out.println("\nSuccess Deleted, Current Stock...");
        } catch (ExceptionHandling e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addISpecialRule(String userInput, StockItems stockItems, SpecialOffers specialOffers) {

        try{
            UIValidator.validateIsEmpty(userInput);
            UIValidator.validateInputFormat(userInput, REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED);
            if(userInput.split(" ").length == 3) {
                String str_nofItems = userInput.split(" ")[1];
                String str_discountPrice = userInput.split(" ")[2];
                UIValidator.validateIsNumeric(str_nofItems);
                UIValidator.validateIsNumeric(str_discountPrice);

                char item = userInput.split(" ")[0].toUpperCase().charAt(0);
                int nofItems = Integer.parseInt(str_nofItems);
                BigDecimal discountPrice = new BigDecimal(str_discountPrice);
                if (nofItems != 0 && !discountPrice.equals(BigDecimal.ZERO)) {//adds pricing rule to whole sale list
                    specialOffers.addSpecialOffer(item, nofItems, discountPrice, stockItems.getProducts(), SPECIALPRICE_FILE);
                    System.out.println("Success Added, current WholeSale...");
                    OutputFileContent(SPECIALPRICES); //reloads current Sales
                    System.out.println(userInput.toUpperCase());
                } else {
                    LOGGER.log(Level.SEVERE, PRICE_ZERO);
                    System.out.println(PRICE_ZERO);
                }

            } else{
                LOGGER.log(Level.SEVERE, ALLDETAILS_REQUIRED);
                System.out.println(ALLDETAILS_REQUIRED);
            }

        } catch (ExceptionHandling e) {
            System.out.println(e.getMessage());
        }

    }

    private static void removeItemFromWholeSale(String userInput, SpecialOffers specialOffers) {
        try{
            UIValidator.validateIsEmpty(userInput);
            UIValidator.validateInputFormat(userInput, REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED);
            if(userInput.split(" ").length == 2) {
                char item = userInput.toUpperCase().charAt(0);
                String str_noOfItems = userInput.split(" ")[1];
                UIValidator.validateIsNumeric(str_noOfItems);

                int noOfItems = Integer.parseInt(str_noOfItems);
                specialOffers.removeSpecialOffer(item, noOfItems, SPECIALPRICE_FILE);    //removes rule from whole sale
                System.out.println("\nSuccess Deleted, Current Stock...");

            } else {
                LOGGER.log(Level.SEVERE, ALLDETAILS_REQUIRED);
                System.out.println(ALLDETAILS_REQUIRED);
            }
        } catch (ExceptionHandling e) {
                System.out.println(e.getMessage());
        }
    }

    public static void OutputFileContent(String fileName) {
        try {
            List<String> inputLineList = utility.readInputFromResource(fileName);
            if(!isNull(inputLineList)) {
                inputLineList.forEach(System.out::println);  //prints out each line of input
            }
        } catch (ExceptionHandling e) {
            System.out.println(NONE_AVAILABLE);
        }
    }

}
