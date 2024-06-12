package org.example.constant;

public class EndPoints {
    public static final String VERSION = "/v1";

    //profiller:
    public static final String API = "/api";
    public static final String DEV = "/dev";
    public static final String TEST = "/test";
    public static final String ROOT = API + VERSION;

    //entityler:
    public static final String URUN = "/urun";
    public static final String CART = "/cart";


    //Methods:

    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String FINDALL = "/findall";
    public static final String FINDBYID = "/findbyid";
    public static final String GETCARTBYUSERID = "/getcartbyuserid";
    public static final String ADDITEMTOCART = "/additemtocart";
    public static final String REMOVEITEMFROMCART = "/removeitemfromcart";
    public static final String CLEARCART = "/clearcart";
    public static final String CHECKOUT = "/checkout";

}
