package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountValidator {
    //Google says that
    private static final String SAVINGS = "savings";
    private static final String SALARY = "salary";
    private static final String CURRENT = "current";

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
        validateType(account.getType());
        validateDate(account.getDate());
        validateBalance(account.getBalance());
        return errors.isEmpty();
    }

    private void validateType(String type) {
        if (!(type.equals(SAVINGS) || type.equals(SALARY) || type.equals(CURRENT))) {
            errors.add("Account type must be 'savings', 'salary' or 'current'!");
        }
    }

    private void validateDate(Date date){
        if(date.after(new Date())){
            errors.add("Date can not be in the future!");
        }
    }

    private void validateBalance(Double balance){
        if(balance < 0){
            errors.add("Balance must be positive!");
        }
    }
}
