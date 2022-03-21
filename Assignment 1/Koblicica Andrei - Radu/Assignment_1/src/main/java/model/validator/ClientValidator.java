package model.validator;

import controller.Response;
import model.Client;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    public static final String CNP_VALIDATION_REGEX="^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    public static final String IDENTITY_CARD_NUMBER_REGEX="[A-Z]{2}\\d{6}";


    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validateEdit(String name, String identityCardNumber, String personalNumericalCode, String address, boolean checkIdentityCardNumber, boolean checkPersonalNumericalCode) {
        errors.clear();
        validateEmpty(name, identityCardNumber, personalNumericalCode, address);
        if(errors.isEmpty()){
            if(checkPersonalNumericalCode) {
                validatePersonalNumericalCode(personalNumericalCode);
                validatePersonalNumericalCodeUniqueness(personalNumericalCode);
            }
            if(checkIdentityCardNumber) {
                validateIdentityCardNumber(identityCardNumber);
                validateIdentityCardNumberUniqueness(identityCardNumber);
            }
        }
    }

    public void validateAdd(String name, String identityCardNumber, String personalNumericalCode, String address){
        errors.clear();
        validateEmpty(name, identityCardNumber, personalNumericalCode, address);
        if(errors.isEmpty()){
            validatePersonalNumericalCode(personalNumericalCode);
            validatePersonalNumericalCodeUniqueness(personalNumericalCode);
            validateIdentityCardNumber(identityCardNumber);
            validateIdentityCardNumberUniqueness(identityCardNumber);
        }
    }



    private void validateEmpty(String name, String identityCardNumber, String personalNumericalCode, String address) {
        if(name.equals("")||identityCardNumber.equals("")||personalNumericalCode.equals("")||address.equals("")){
            errors.add("Make sure you complete all fields!");
        }
    }

    private void validateIdentityCardNumberUniqueness(String identityCardNumber) {
        final Response<Boolean> response = clientRepository.existsIdentityCardNumber(identityCardNumber);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Identity card number already exists!");
            }
        }

    }

    private void validateIdentityCardNumber(String identityCardNumber){
        if(!identityCardNumber.matches(IDENTITY_CARD_NUMBER_REGEX)){
            errors.add("Identity card number is not valid!");
        }
    }

    private void validatePersonalNumericalCode(String personalNumericalCode) {
        if(!personalNumericalCode.matches(CNP_VALIDATION_REGEX)){
            errors.add("Personal numerical code is not valid!");
        }
    }

    private void validatePersonalNumericalCodeUniqueness(String personalNumericalCode){
        final Response<Boolean> response = clientRepository.existsPersonalNumericalCode(personalNumericalCode);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Personal numerical code already exists!");
            }
        }
    }


    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
