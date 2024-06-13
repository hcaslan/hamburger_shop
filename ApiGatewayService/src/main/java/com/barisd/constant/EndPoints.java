package com.barisd.constant;

public class EndPoints {
    public static final String VERSION = "/v1";

    //profiller:
    public static final String API = "/api";
    public static final String DEV = "/dev";
    public static final String TEST = "/test";
    public static final String ROOT = API + VERSION;

    //entityler:
    public static final String AUTH = ROOT + "/auth";
    public static final String PROFILE = ROOT + "/profile";
    public static final String EMAIL = ROOT + "/email";
    public static final String CART = ROOT + "/cart";
    public static final String URUN = ROOT + "/urun";
    public static final String ADDRESS = ROOT + "/address";
    public static final String SHOPPING = ROOT + "/shopping";
}