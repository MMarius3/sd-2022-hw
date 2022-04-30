package model.validator;

import java.util.ArrayList;
import java.util.List;

public class DateValidator {
    private final String DATE_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    private final List<String> errors = new ArrayList<>();

    public DateValidator() {
    }

    public void validate(String date) {
        errors.clear();
        validateDateFormat(date);
    }

    private void validateDateFormat(String date) {
        if (!date.matches(DATE_REGEX)) {
            errors.add("Date is not valid");
        }
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
