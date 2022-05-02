package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOKS = API_PATH + "/books";
    public static final String ENTITY = "/{id}";
    public static final String FILTERED = "/filtered/{title}/{author}/{genre}/{quantity}";
    public static final String EXPORT_REPORT = "/export/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";

}
