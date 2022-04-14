package model.validator;

import repository.client_account.ClientAccountRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientAccountValidator {

    private static final String DATE_VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String AMOUNT_VALIDATION_REGEX = "^[0-9]*\\.[0-9]{2}$";
    private static final Integer MAX_LENGTH = 100;

    private final List<String> errors = new ArrayList<>();
    private final ClientAccountRepository clientAccountRepository;

    public ClientAccountValidator(ClientAccountRepository clientAccountRepository) {
        this.clientAccountRepository = clientAccountRepository;
    }

    public void validate(String type, String amount, String date){
        errors.clear();
        validateType(type);
        validateAmount(amount);
        validateDate(date);
    }

    private void validateType(String type){
        if(type.equals("") || type == null){
            errors.add("type must be filled");
        }
        else
        if(type.length() > MAX_LENGTH){
            errors.add("Type is too long");
        }
    }

    private void validateAmount(String amount){
        if(amount.equals("") || amount == null){
            errors.add("amount must be filled");
        }
        else
        if(!amount.matches(AMOUNT_VALIDATION_REGEX)){
            errors.add("Amount is not a double with two decimals");
        }
    }

    public void validateDate(String date){
        if(date.equals("") || date == null){
            errors.add("date must be filled");
        }
        else
        if(!date.matches(DATE_VALIDATION_REGEX)){
            errors.add("Date must be of type YYYY-MM-DD");
        }
    }

    public String getValidateAmountError(String amount){
        if(amount.equals("") || amount == null){
            return "amount must be filled";
        }
        else
        if(!amount.matches(AMOUNT_VALIDATION_REGEX)){
            return "Amount is not a double with two decimals";
        }
        return "";
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
