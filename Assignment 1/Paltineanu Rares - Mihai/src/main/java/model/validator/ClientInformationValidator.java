package model.validator;

import model.Client;
import repository.client.ClientRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ClientInformationValidator {

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientInformationValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validate(String name, String personalNumericalCode, boolean verifyPNCUniqueness) {
        errors.clear();
        validatePersonalNumericalCode(personalNumericalCode);
        validateName(name);
        if(verifyPNCUniqueness) {
            existsByCNP(personalNumericalCode);
        }
    }

    private void existsByCNP(String personalNumericalCode) {
        Client client = clientRepository.findByCNP(personalNumericalCode);
        if(client != null) {
            errors.add("Client with PNC " + personalNumericalCode + " already exists");
        }
    }

    private void validatePersonalNumericalCode(String personalNumericalCode) {
        if (!personalNumericalCode.matches("^(\\d{13})?$")) {
            errors.add("Personal numerical code must contain 13 digits");
        }
    }

    private void idDateValid(Date date) {

    }

    private void validateName(String name) {
        if (name.matches(".*[0-9].*") || name.matches(".*[!@#$%^&*()_+].*")) {
            errors.add("The name must contain only alphabetic characters");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
