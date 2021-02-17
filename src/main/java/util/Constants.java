package util;

public final class Constants {
    //Constant Interface Anti-pattern
    private Constants() {}  //private constructor hence this class is non-instantiable.
    public static String ITEMS = "Items";
    public static String SPECIALPRICES = "SpecialPrices";
    public static String SPECIALPRICE_FILE = "src/main/resources/SpecialPrices";
    public static String ITEMS_FILE = "src/main/resources/Items";
    public static String TEST_ITEM_FILE = "src/test/resources/Items";
    public static String TEST_SPECIALPRICE_FILE = "src/test/resources/SpecialPrices";
    public static String NULLFOUND = "Product is null";
    public static String FILEOPERAATION_SUCCESSFUL = "File Operation successfully!";
    public static String PRICINGRULE_EXIST = "Special Pricing already exist";
    public static String PRICINGRULE_NOTPRESENT = "Special Pricing does not exist";
    public static String ITEM_NOTFOND = "Item Specified not Found";
    public static String ITEM_EXIST = "Item Specified Already Exist";
    public static String NUMERIC_ERROR = "Must be an integer";
    public static String PRICE_ZERO = "Value must be greater than 0";
    public static String EMPTY_STOCK = "Empty Stock";
    public static String EMPTY_SALES = "Empty Sales";
    public static String ALLDETAILS_REQUIRED = "Invalid Format, Must enter all required Fields";
    public static String ONLY_LETTERS_ALLOWED = "Only Letters allowed";
    public static String ONLY_LETTERS_AND_NUMBERS_ALLOWED = "Only Letters and Numbers allowed";
    public static String INTERNAL_SERVER_ERROR = "An Internal Server Error Occurred";
    public static String NONE_AVAILABLE = "None available";
    public static String REGEX_ONLY_LETTERS = "^[A-Za-z]*$";
    public static String REGEX_ONLY_LETTERS_AND_NUMBERS = "[A-z].[0-9]+.*$";
    public static String FILE_DOES_NOT_EXISTS = "File does not Exist";

}
