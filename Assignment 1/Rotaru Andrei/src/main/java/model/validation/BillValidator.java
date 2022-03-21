package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class BillValidator {
    public List<String> getErrors() {
        return errors;
    }

    private final Account account;
    private final Double sum;
    private final List<String> errors;

    public BillValidator(Account account, Double sum){
        this.account = account;
        this.sum = sum;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateSum(sum);
        validateBalance(account.getBalance(), sum);
        return errors.isEmpty();
    }

    private void validateSum(Double sum){
        if (sum < 0) {
            errors.add("Paid sum must be positive!");
        }
    }

    private void validateBalance(Double balance, Double sum) {
        if (balance < sum) {
            errors.add("Balance is too low!");
        }
    }
}
