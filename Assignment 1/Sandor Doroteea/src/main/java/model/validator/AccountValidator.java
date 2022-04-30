package model.validator;

import repository.account.AccountRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class AccountValidator {

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validate(String balance, String type, Date dateOfCreation, String client_id){
        errors.clear();
        validateBalance(balance);
        validateType(type);
        validateDate(dateOfCreation);
        validateClientId(client_id);

    }

    private void validateBalance(String balance){
        if(!balance.matches("^[0-9]*$")){
            errors.add("Balance is not a valid number.");
        }
    }
    private void validateClientId(String client_id){
        if(!client_id.matches("^[0-9]*$")){
            errors.add("Client id is not valid.");
        }
    }

    private void validateDate(Date date){
        java.util.Date today = new java.util.Date();
        if(date.after(today)){
            errors.add("Date does not exist yet!");
        }
    }
    private void validateType(String type){
        if(!Objects.equals(type, "Savings") && !Objects.equals(type, "Checking")){
            errors.add("Account doesn't have type Savings or Checking so it's not a valid account.");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }


}
