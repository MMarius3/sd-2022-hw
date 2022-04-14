package model.validator;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private static final String IDENTIFICATION_NUMBER_REGEX = "(\\b\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\b)";
    private static final String MONEY_REGEX = "^\\$?(([1-9](\\d*|\\d{0,2}(,\\d{3})*))|0)(\\.\\d{1,2})?$";
    private static final String DATE_REGEX = "^(?:31([/\\-.])(?:0?[13578]|1[02])\\1|(?:29|30)([/\\-.])(?:0?[13-9]|1[0-2])\\2)(?:1[6-9]|[2-9]\\d)?\\d{2}$|^29([/\\-.])0?2\\3(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:16|[2468][048]|[3579][26])00)$|^(?:0?[1-9]|1\\d|2[0-8])([/\\-.])(?:0?[1-9]|1[0-2])\\4(?:1[6-9]|[2-9]\\d)?\\d{2}$";

    private final List<String> errors = new ArrayList<>();

    public AccountValidator() {
    }

    public void validate(Account account){
        errors.clear();
        validateIdentificationNumber(account);
        validateAmountOfMoney(account);
        validateDateOfCreation(account);
    }

    private void validateIdentificationNumber(Account account){
        if(!account.getIdentificationNumber().matches(IDENTIFICATION_NUMBER_REGEX)){
            errors.add("Identification number not valid!");
        }
    }

    private void validateAmountOfMoney(Account account){
        if(!account.getAmountOfMoney().matches(MONEY_REGEX)){
            errors.add("Amount of money not valid!");
        }
    }

    private void validateDateOfCreation(Account account){
        if(!account.getDateOfCreation().matches(DATE_REGEX)){
            errors.add("Date of creation not valid!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
