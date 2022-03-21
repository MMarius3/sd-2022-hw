package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class TransferValidator {
    public List<String> getErrors() {
        return errors;
    }

    private final Account account1;
    private final Account account2;
    private final Double sum;
    private final List<String> errors;

    public TransferValidator(Account account1, Account account2, Double sum){
        this.account1 = account1;
        this.account2 = account2;
        this.sum = sum;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateSum(sum);
        validateBalance(account1.getBalance(), sum);
        return errors.isEmpty();
    }

    private void validateSum(Double sum){
        if (sum < 0) {
            errors.add("Transferred sum must be positive!");
        }
    }

    private void validateBalance(Double balance, Double sum) {
        if (balance < sum) {
            errors.add("Balance is too low!");
        }
    }
}
