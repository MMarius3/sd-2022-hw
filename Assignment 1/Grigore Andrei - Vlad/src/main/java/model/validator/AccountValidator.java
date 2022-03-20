package model.validator;

import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountValidator {
    public static final String CARD_VALIDATION_REGEX = "\\b((4\\d{3}|5[1-5]\\d{2}|2\\d{3}|3[47]\\d{1,2})[\\s\\-]?\\d{4,6}[\\s\\-]?\\d{4,6}?([\\s\\-]\\d{3,4})?(\\d{3})?)\\b";
    private final List<String> errors = new ArrayList<>();

    private final String[] types = {"VISA","MASTERCARD"};

    public AccountValidator(){}

    public void validate(String card,String type,Long balance){
        errors.clear();
        validateBalance(balance);
        validateType(type);
        validateCard(card);
    }
    private void validateCard(String card){
        if(!card.matches(CARD_VALIDATION_REGEX)){
            errors.add("Card number is not valid");
        }
    }

    private void validateBalance(Long balance){
        if(balance<50){
            errors.add("Not enough money");
        }
    }

    private void validateType(String type){
        if(!Arrays.stream(types).anyMatch(p->p.equals(type))){
            errors.add("Invalid card type");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
