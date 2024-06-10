package com.kerem.constant;

public class EndPoints {
    public static final String VERSION = "/v1";

    //profiller:
    public static final String API = "/api";
    public static final String DEV = "/dev";
    public static final String TEST = "/test";
    public static final String ROOT = API + VERSION;

    //entityler:
    public static final String AUTH = "/auth";
    public static final String YARISMA = "/yarisma";
    public static final String USER = "/user";
    public static final String SORU = "/soru";

    //Methods:

    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String FINDALL = "/findall";
    public static final String FINDBYID = "/findbyid";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String ACTIVATEACCOUNT = "/activateaccount";
    public static final String CREATETOKENFROMID = "/createtokenfromid";
    public static final String CREATETOKENFROMIDANDROLE = "/createtokenfromidandrole";
    public static final String GETIDFROMTOKEN = "/getidfromtoken";
    public static final String GETROLEFROMTOKEN = "/getrolefromtoken";
    public static final String FORGOTPASSWORD = "/forgotpassword";
    public static final String RESETPASSWORD = "/resetpassword";
    public static final String GETRESETPASWORDCODE = "/getresetpasswordcode";
    public static final String CHANGEPASSWORD = "/changepassword";
    public static final String DELETEMYACCOUNT = "/deletemyaccount";
}
