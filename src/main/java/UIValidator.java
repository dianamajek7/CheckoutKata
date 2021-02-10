import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static util.Constants.NULLFOUND;
import static util.Constants.NUMERIC_ERROR;

public class UIValidator {


    public static String isNumeric(String inputLine, Logger logger) {
        if(isNull(inputLine)){
            logger.log(Level.SEVERE, NULLFOUND);
            return NULLFOUND;
        }

        try {
            Integer.parseInt(inputLine);    //validates a numeric value by casting the input to a primitive int

        } catch (NumberFormatException nfe) {
            logger.log(Level.SEVERE, NUMERIC_ERROR);
            return NUMERIC_ERROR;
        }
        return null;
    }
}
