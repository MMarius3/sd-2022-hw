package model.validator;

import controller.Response;
import repository.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    private static final String CARD_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
    private static final String TYPE_REGEX = "^VISA|MASTERCARD|AMERICANEXPRESS$";

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validate(String cardNumber, String type, Float sum){
        errors.clear();
        verifySum(sum);
        verifyCard(cardNumber);
        verifyCardUniqueness(cardNumber);
        verifyType(type);
    }

    public void validateSumOnly(Float sum){
        verifySum(sum);
    }
    public void validateCardOnly(String cardNumber){
        verifyCard(cardNumber);
        verifyCardUniqueness(cardNumber);
    }
    public void validateTypeOnly(String type){
        verifyType(type);
    }

    private void verifySum(Float sum){
        if(sum < 0){
            errors.add("Sum has to be bigger than 0!");
        }
    }

    private void verifyCard(String cardNumber){
        if(!cardNumber.matches(CARD_REGEX)){
            errors.add("Enter a valid card number!");
        }
    }

    private void verifyCardUniqueness(String cardNumber){
        final Response<Boolean> response = accountRepository.existsByCardNumber(cardNumber);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Card number already exists!");
            }
        }
    }


    private void verifyType(String type){
        if(!type.matches(TYPE_REGEX)){
            errors.add("Enter a valid card type between VISA/MASTERCARD/AMERICANEXPRESS");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
