import Shopping.Basket;
import Shopping.Checkout;
import Stock.StockItems;
import Wholesale.SpecialOffers;
import util.Utility;

import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class UI {
    private static final Logger LOGGER = Logger.getLogger( SpecialOffers.class.getName() );
    static StockItems stockItems = new StockItems();
    static SpecialOffers specialOffers =  new SpecialOffers();
    static Basket basket = new Basket(stockItems);
    static Checkout checkout = new Checkout(specialOffers);

    public static void main(String[] args) {
        Utility.initialise(stockItems, specialOffers);
        System.out.println("Welcome");
        System.out.println("Enter 1 to Add item to your basket" );
        System.out.println("Enter 2 to Modify Stocks" );
        System.out.println("Enter 3 to Modify SpecialPrice\n" );


        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        String errorMessage = UIValidator.isNumeric(userInput, LOGGER);
        if(isNull(errorMessage)) {
            menu(Integer.parseInt(userInput));  //loads each UI path, plays a role of a route
        }else{
            System.out.println(errorMessage);
        }
    }

    private static void menu(int option) {

        switch(option) {
            case 1:
                UIMapper.shopping(basket, checkout, specialOffers);
                break;
            case 2:
                UIMapper.modifyStocks(stockItems);
                break;
            case 3:
                UIMapper.modifySpecialPrice(stockItems, specialOffers);
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }

    }


}
