package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final String PERSONAL_NUMBER_VALIDATION_REGEX = "^[0-9]+$";
    private static final Integer MIN_IDENTIFICATION_NUMBER = 10000000;
    private static final Integer MAX_IDENTIFICATION_NUMBER = 99999999;

    private final Client client;
    private final List<String> errors;

    public List<String> getErrors() {

        return errors;
    }

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateIdentityCardNumber(client.getIdentificationNr());
        validatePersonalNumericalCode(client.getPersonalNumericalCode());
        return errors.isEmpty();
    }

    private void validateIdentityCardNumber(Integer identityCardNumber) {
        if (identityCardNumber < MIN_IDENTIFICATION_NUMBER) {
            errors.add("The identity card should have 8 digits. Please try again.");
        }
        if (identityCardNumber > MAX_IDENTIFICATION_NUMBER) {
            errors.add("The identity card should have 8 digits. Please try again.");
        }
    }

    private void validatePersonalNumericalCode(String personalNumericalCode) {

        if (!Pattern.compile(PERSONAL_NUMBER_VALIDATION_REGEX).matcher(personalNumericalCode).matches()) {
            errors.add("The numerical code is invalid. It must contain only numbers!");
        }
    }

}
