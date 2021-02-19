package com.challenge.checkoutKata;

import com.challenge.checkoutKata.util.ExceptionHandling;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.challenge.checkoutKata.util.Constants.NULLFOUND;
import static com.challenge.checkoutKata.util.Constants.NUMERIC_ERROR;
import static java.util.Objects.isNull;

public class UIValidator {
    private static final Logger LOGGER = Logger.getLogger( UIValidator.class.getName() );

    public static void validateIsNumeric(String inputLine) throws ExceptionHandling {

        validateIsEmpty(inputLine);
        try {
            Integer.parseInt(inputLine);    //validates a numeric value by casting the input to a primitive int

        } catch (NumberFormatException e) {
            String msg = NUMERIC_ERROR;
            LOGGER.log(Level.SEVERE, msg);
            throw new ExceptionHandling(msg);
        }
    }

    public static void validateIsEmpty(String inputLine) throws ExceptionHandling {
        if(isNull(inputLine)){
            String msg = NULLFOUND;
            LOGGER.log(Level.SEVERE, msg);
            throw new ExceptionHandling(msg);
        }
    }

    public static void validateInputFormat(String inputLine, String regex, String errorMessage) throws ExceptionHandling {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(inputLine);
        boolean flag = match.find();
        if (!flag){
            LOGGER.log(Level.SEVERE, errorMessage);
            throw new ExceptionHandling(errorMessage);
        }

    }

}
