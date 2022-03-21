package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private static final int MIN_ICN_LENGTH = 6;
    private static final int MIN_PNC_LENGTH = 13;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateName(client.getName());
        validateIdentityCardNumber(client.getIdentityCardNumber());
        validatePersonalNumericalCode(client.getPersonalNumericalCode());
        validateAddress(client.getAddress());
        return errors.isEmpty();
    }

    private void validateName(String name) {
        String[] names = name.split(" ");
        if (names.length < 2) {
            errors.add("Please enter both name and surname!");
        }
    }

    private void validateIdentityCardNumber(String identityCardNumber){
        if(identityCardNumber.length() != MIN_ICN_LENGTH){
            errors.add("Identity Card Number must be of 6 digits!");
        }
        if(!containsOnlyDigits(identityCardNumber)){
            errors.add("Identity Card Number must contain only digits!");
        }
    }

    private void validatePersonalNumericalCode(String personalNumericalCode){
        if(personalNumericalCode.length() != MIN_PNC_LENGTH){
            errors.add("Personal Numerical Code must be of 13 digits!");
        }
        if(!containsOnlyDigits(personalNumericalCode)){
            errors.add("Personal Numerical Code must contain only digits!");
        }
    }

    private void validateAddress(String address){
        if(address.isEmpty()){
            errors.add("Address must not be empty!");
        }
    }

    private boolean containsOnlyDigits(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}
