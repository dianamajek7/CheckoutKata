import Shopping.Basket;
import Shopping.Checkout;
import Wholesale.SpecialOffers;
import util.SkuUtil;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static util.Constants.ITEMS;

public class UIMapper {
    private static final Logger LOGGER = Logger.getLogger( SpecialOffers.class.getName() );
    static SkuUtil skuUtil = new SkuUtil();

    public static void loadBasket(){

        System.out.println("\nBelow are the list of available Items...");
        List<String> inputLineList = skuUtil.readInputFromResource(ITEMS);
        inputLineList.forEach(System.out::println);  //prints out each line of input

        System.out.println("\nAdd items to your basket i.e(AAAB): " );
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String errorMessage = Validator.validateIsNull(userInput, LOGGER);
        if(isNull(errorMessage)) {
            Basket basket = new Basket();
            errorMessage = basket.addItemToBasket(userInput);

            if(!isNull(errorMessage))
                System.out.println(errorMessage);

            Checkout checkout = new Checkout();
            checkout.calculateTotal(basket.getShoppingBasket());

            float total = checkout.getTotal();
            System.out.println("Receipt.......");
            basket.getItemsTotal().forEach((product, itemTotal) -> {
                System.out.println("Item: "+ product.getName());
                System.out.println("Item Total: "+ itemTotal);
            });

            System.out.println("Total price: "+ total);

        }else{
            System.out.println(errorMessage);
        }
    }

    public static void modifyStocks(){

    }

    public static void modifySpecialPrice(){

    }

}
