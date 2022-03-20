package model.validator;

import org.apache.commons.validator.GenericValidator;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateValidator {

    private final List<String> errors = new ArrayList<>();

    public DateValidator() {
    }

    public void validate(String fromYear, String fromMonth, String fromDay, String toYear, String toMonth, String toDay) {
        validateFromDate(fromYear, fromMonth, fromDay);
        validateToDate(toYear, toMonth, toDay);

        if(errors.isEmpty()) {
            int fromYearInt = Integer.parseInt(fromYear);
            int fromMonthInt = Integer.parseInt(fromMonth);
            int fromDayInt = Integer.parseInt(fromDay);

            int toYearInt = Integer.parseInt(toYear);
            int toMonthInt = Integer.parseInt(toMonth);
            int toDayInt = Integer.parseInt(toDay);
            validateDateOrder(new Date(fromYearInt, fromMonthInt, fromDayInt), new Date(toYearInt, toMonthInt, toDayInt));
        }
    }
    public void validateFromDate(String year, String month, String day) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.UK);

        try {
            dateTimeFormatter.parse(year + '-' + month + '-' + day);
        } catch (DateTimeParseException e) {
            errors.add("From Date is not valid");
        }
    }

    public void validateToDate(String year, String month, String day) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.UK);

        try {
            dateTimeFormatter.parse(year + '-' + month + '-' + day);
        } catch (DateTimeParseException e) {
            errors.add("To Date is not valid");
        }
    }

    public void validateDateOrder(Date fromDate, Date toDate) {
        if(fromDate.after(toDate)) {
            errors.add("Inverted dates");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
