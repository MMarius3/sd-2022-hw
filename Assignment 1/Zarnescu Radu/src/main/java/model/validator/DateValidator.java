package model.validator;

import java.util.ArrayList;
import java.util.List;

public class DateValidator {

    private final List<String> errors = new ArrayList<>();

    private static final String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";

    public void validateDate(String date) {
        if (!date.matches(DATE_REGEX)) {
            errors.add("Invalid date");
        }
    }

    public void validateDates(String d1, String d2) {
        validateDate(d1);
        validateDate(d2);
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
