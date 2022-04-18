package com.application.controller;

// TODO -> package separat de url mappings organizat pe controllere
public class UrlMappings {

    public static final String URL_PREFIX = "/clinic";

    public static final String SECRETARY = URL_PREFIX + "/secretary";
    public static final String DOCTOR = URL_PREFIX + "/doctor";
    public static final String ADMINISTRATOR = URL_PREFIX + "/administrator";

    public static final String SIGN_UP = "/signup";
    public static final String SIGN_IN = "/signin";

    public static final String GET_USER_ACCOUNT = "/getUserAccount";
    public static final String CREATE_USER_ACCOUNT = "/createUserAccount";
    public static final String UPDATE_USER_ACCOUNT = "/updateUserAccount";
}
