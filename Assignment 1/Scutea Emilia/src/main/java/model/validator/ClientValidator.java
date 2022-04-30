package model.validator;

import controller.Response;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final String CNP_VALIDATION_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private static final String CARD_VISA_VALIDATION_REGEX = "^4[0-9]{12}(?:[0-9]{3})?$";
    private static final String CARD_MASTERCARD_VALIDATION_REGEX = "^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$";
    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validateAdd(String name, Long cardNumber, Long personalNumericalCode, Long accountNumber) {
        errors.clear();
        validatePersonalNumericalCodeUniqueness(personalNumericalCode);
        validatePersonalNumericalCode(personalNumericalCode);
        validateAccountNumber(accountNumber);
        validateCardNumber(cardNumber);
        validateNameUniqueness(name);
    }

    public void validateUpdate(String name, Long cardNumber, Long personalNumericalCode, Long accountNumber) {
        errors.clear();
        validatePersonalNumericalCode(personalNumericalCode);
        validateAccountNumber(accountNumber);
        validateCardNumber(cardNumber);
    }

    private void validateNameUniqueness(String name){
        final Response<Boolean> response = clientRepository.existsByName(name);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Client with this name already exists");
            }
        }
    }


    private void validatePersonalNumericalCodeUniqueness(Long personalNumericalCode) {
        final Response<Boolean> response = clientRepository.existsByCNP(personalNumericalCode);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Client with this CNP already exists");
            }
        }
    }

    private void validatePersonalNumericalCode(Long personalNumericalCode) {
        if (!personalNumericalCode.toString().matches(CNP_VALIDATION_REGEX)) {
            errors.add("Personal numerical code is not valid");
        }
    }

    private void validateAccountNumber(Long accountNumber) {
        if (accountNumber <= 0) {
            errors.add("Account number is not valid");
        }
    }

    private void validateCardNumber(Long cardNumber){
        if(!cardNumber.toString().matches(CARD_VISA_VALIDATION_REGEX) && !cardNumber.toString().matches(CARD_MASTERCARD_VALIDATION_REGEX)){
            errors.add("Card number is not valid ");
        }
    }

    public List<String> getErrors(){
        return errors;
    }

    public String getFormattedErrors(){
        return String.join("\n", errors);
    }
}
