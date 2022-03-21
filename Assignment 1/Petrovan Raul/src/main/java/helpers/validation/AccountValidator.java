package helpers.validation;

import model.Account;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AccountValidator {
    private static final String ACCOUNT_NUMBER_VALIDATOR = "^RO\\d{2}[A-Z]{4}[A-Z0-9]{16}$";

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Account account;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateAccountNumber();
        return errors.isEmpty();
    }

    private void validateAccountNumber() {
        if (!Pattern.compile(ACCOUNT_NUMBER_VALIDATOR).matcher(account.getAccountNumber()).matches()) {
            errors.add("Invalid Account Number! Example: RO49AAAA1B31007593840000");
        }
    }
}
