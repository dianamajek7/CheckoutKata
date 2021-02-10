package util;

public final class Constants {
    //Constant Interface Anti-pattern
    private Constants() {}  //private constructor hence this class is non-instantiable.
    public static String ITEMS = "Items";
    public static String SPECIALPRICES = "SpecialPrices";
    public static String TEST_ITEM_FILE = "tests/resources/Items";
    public static String TEST_SPECIALPRICE_FILE = "tests/resources/SpecialPrices";
    public static String NULLFOUND = "Product is null";
    public static String FILEOPERAATION_SUCCESSFUL = "File Operation successfully!";
    public static String PRICINGRULE_EXIST = "Special Pricing already exist";
    public static String PRICINGRULE_NOTPRESENT = "Special Pricing does not exist";
    public static String ITEM_NOTFOND = "Item Specified not Found";
    public static String ITEM_EXIST = "Item Specified Already Exist";
    public static String NUMERIC_ERROR = "Must be an integer";

}
