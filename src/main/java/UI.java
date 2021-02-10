import Shopping.Basket;
import Wholesale.SpecialOffers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static util.Constants.NULLFOUND;
import static util.Constants.NUMERIC_ERROR;

public class UI {
    private static final Logger LOGGER = Logger.getLogger( SpecialOffers.class.getName() );
    public static void main(String[] args) {

        System.out.println("Welcome");
        System.out.println("Enter 1 to Add item to your basket" );
        System.out.println("Enter 2 to Modify Stocks" );
        System.out.println("Enter 3 to Modify SpecialPrice\n" );


        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        String errorMessage = Validator.isNumeric(userInput, LOGGER);
        if(isNull(errorMessage)) {
            menu(Integer.parseInt(userInput));
        }else{
            System.out.println(errorMessage);
        }


    }

    public static void menu(int option) {

        switch(option) {
            case 1:
                UIMapper.loadBasket();
                break;
            case 2:
                UIMapper.modifyStocks();
                break;
            case 3:
                UIMapper.modifySpecialPrice();
                break;
        }

    }


}
