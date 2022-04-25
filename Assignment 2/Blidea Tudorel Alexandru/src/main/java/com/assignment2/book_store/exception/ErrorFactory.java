package com.assignment2.book_store.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ErrorFactory {

    private static final Map<HttpStatus, String> errorMapDefaultMessages = new HashMap<>() {{
        put(HttpStatus.I_AM_A_TEAPOT, "The server is just a teapot, sry!");
        put(HttpStatus.BAD_REQUEST, "Bad Request, sry!");
        put(HttpStatus.UNAUTHORIZED, "I've never seen that man in my whole life!");
        put(HttpStatus.FORBIDDEN, "You have no power here");
        put(HttpStatus.NOT_FOUND, "I can't find it!");
        put(HttpStatus.CONFLICT, "It's a conflict, let's fight!");
        put(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, please try again later!");
        put(HttpStatus.NOT_IMPLEMENTED, "Lazy developer, not implemented :(");
    }};
    private static final String DEFAULT_MESSAGE = "default message!";

    private static String getMessage(HttpStatus errorCode) {
        return Optional.ofNullable(errorMapDefaultMessages.get(errorCode))
                .orElse(DEFAULT_MESSAGE);
    }

    public static ApplicationError getError(HttpStatus errorCode) {
        return new ApplicationError(getMessage(errorCode), errorCode);
    }

    public static ApplicationError getError(HttpStatus errorCode, String message) {
        return new ApplicationError(message, errorCode);
    }

}
