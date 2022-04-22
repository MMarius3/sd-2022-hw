package com.example.bookstore;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String GET_USERS = "/get-users";
    public static final String GET_USER = "/get-users/{id}";
    public static final String DELETE_USER = "delete-user/{id}";
    public static final String ADD_USER = "/add-user";

    public static final String BOOKS = "/books";
    public static final String UPDATE_BOOK = "/update-book/{id}";
    public static final String ADD_BOOK = "/add-book";
    public static final String GET_BOOK = "/get-book/{id}";
    public static final String GET_BOOKS = "/get-books";
    public static final String DELETE_BOOK = "/delete-book/{id}";
}
