package com.challenge.checkoutKata;

import com.challenge.checkoutKata.shopping.Basket;
import com.challenge.checkoutKata.shopping.Checkout;
import com.challenge.checkoutKata.stock.StockItems;
import com.challenge.checkoutKata.util.ExceptionHandling;
import com.challenge.checkoutKata.util.Utility;
import com.challenge.checkoutKata.wholesale.SpecialOffers;

import java.util.Scanner;

import static com.challenge.checkoutKata.util.Constants.INVALID_OPTION;

public class UI {
    private static final Utility utility = new Utility();
    private static final StockItems stockItems = new StockItems();
    private static final SpecialOffers specialOffers =  new SpecialOffers();
    private static final Basket basket = new Basket(stockItems);
    private static final Checkout checkout = new Checkout(specialOffers);


    public static void main(String[] args) {

        try{
            utility.initialise(stockItems, specialOffers);
            System.out.println("Welcome");
            System.out.println("Enter 1 to Add item to your basket" );
            System.out.println("Enter 2 to Modify Stocks" );
            System.out.println("Enter 3 to Modify SpecialPrice\n" );

            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            UIValidator.validateIsNumeric(userInput);
            menu(Integer.parseInt(userInput));  //loads each UI path, plays a role of a route
        } catch (ExceptionHandling e) {
            System.out.println(e.getMessage());
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
                System.out.println(INVALID_OPTION);
                break;
        }

    }


}
