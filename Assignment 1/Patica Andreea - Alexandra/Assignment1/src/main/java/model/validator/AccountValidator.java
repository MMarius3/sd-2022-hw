package model.validator;

import repository.account.AccountRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    private static final String TYPE_VALIDATION_REGEX = "^(checking_account|savings_account|money_making_deposit_account|certificate_of_deposit)$";
    private static final String BALANCE_VALIDATION_REGEX = "^\\d*$";

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validate(String type, String balance){
        errors.clear();
        validateNull(type);
        validateNull(balance);
        validateType(type);
        validateBalance(balance);
    }

    private void validateNull(String text){
        if (text == null){
            errors.add("Incomplete fields");
        }
    }

    private void validateType(String type) {
        if (!type.matches(TYPE_VALIDATION_REGEX)){
            errors.add("Type is not valid");
        }
    }

    private void validateBalance(String balance) {
        if (!balance.matches(BALANCE_VALIDATION_REGEX)) {
            errors.add("Balance is not a number");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
