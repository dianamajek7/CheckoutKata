import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static util.Constants.NULLFOUND;
import static util.Constants.NUMERIC_ERROR;

public class Validator {
    public static String validateIsNull(String inputLine, Logger logger) {
        if (isNull(inputLine)){
            logger.log(Level.SEVERE, NULLFOUND);
            return NULLFOUND;
        }
        return null;
    }


    public static String isNumeric(String inputLine, Logger logger) {
        String msg = validateIsNull(inputLine, logger);
        if(msg != null)
            return msg;

        try {
            Integer.parseInt(inputLine);

        } catch (NumberFormatException nfe) {
            logger.log(Level.SEVERE, NUMERIC_ERROR);
            return NUMERIC_ERROR;
        }
        return null;
    }
}
