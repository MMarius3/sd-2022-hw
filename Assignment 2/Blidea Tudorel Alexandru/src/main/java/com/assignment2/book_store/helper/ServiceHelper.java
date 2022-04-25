package com.assignment2.book_store.helper;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ServiceHelper {

    private static final String DATE_DEFAULT_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter SERVER_DEFAULT_DATE_FROMATTER = DateTimeFormatter.ofPattern(DATE_DEFAULT_PATTERN);

    public static String dateToString(LocalDate date) {
        return date.format(SERVER_DEFAULT_DATE_FROMATTER);
    }

    public static LocalDate stringToDate(String date) {
        return LocalDate.parse(date, SERVER_DEFAULT_DATE_FROMATTER);
    }

    public static String getDateDefaultPattern() {
        return DATE_DEFAULT_PATTERN;
    }

}
