package com.example.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ITEMS = API_PATH + "/items";
    public static final String ITEMS_ID_EDIT = "/edit/{id}";
    public static final String EXPORT_REPORT = "/export/{type}";
    public static final String ITEMS_ID_DELETE = "/delete/{id}";


    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";
    public static final String USERS_ID_EDIT = "/edit/{id}";
    public static final String USERS_ID_DELETE = "/delete/{id}";

    public static final String BOOKSTORE = API_PATH + "/bookstore";
    public static final String BOOKSTORE_ID_SELL = "/sell/{id}";
    public static final String BOOKSTORE_SEARCH_ITEMS = "/search/{str}";

}
