package com.assignment2.book_store.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApplicationError extends RuntimeException {

    private static final String ILLEGAL_VALUE_DEFAULT_MESSAGE = "Illegal value for %s: %s";

    private HttpStatus errorCode;

    public ApplicationError(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationError() {
        super();
    }

    public static ApplicationError of(String message, HttpStatus errorCode) {
        return new ApplicationError(message, errorCode);
    }

    public static ApplicationError of() {
        return new ApplicationError();
    }

    public static ApplicationError ofIllegalValueOf(String fieldName, String value) {
        String errorMessage = String.format(ILLEGAL_VALUE_DEFAULT_MESSAGE, fieldName, value);
        return new ApplicationError(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
