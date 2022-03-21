package model.validation;

import model.Client;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {
    private static final String CNP_VALIDATION_REGEX = "^(\\d{13})?$";
    private static final String IC_NO_VALIDATION_REGEX = "^[A-Z]{2}[0-9]{6}$";

    private final ClientRepository clientRepository;
    private final List<String> errors;
    private final Client client;

    public ClientValidator(ClientRepository clientRepository, Client client) {
        this.clientRepository = clientRepository;
        errors = new ArrayList<>();
        this.client = client;
    }

    public boolean validate() {
        validateCNP(client.getCNP());
        validateCardNo(client.getIdCardNo());
        return errors.isEmpty();
    }

    private void validateCNP(String cnp) {
        if (!Pattern.compile(CNP_VALIDATION_REGEX).matcher(cnp).matches()) {
            errors.add("Invalid cnp!");
        }
        validateCNPUniqueness(cnp);
    }

    public void validateCNPUniqueness(String cnp) {
        boolean response = clientRepository.existsCNP(cnp);
        if (response) {
            errors.add("CNP already exists.");
        }
    }

    private void validateCardNo(String cardNo) {
        if (!Pattern.compile(IC_NO_VALIDATION_REGEX).matcher(cardNo).matches()) {
            errors.add("Invalid identity card number!");
        }
        validateICNoUniqueness(cardNo);
    }

    public void validateICNoUniqueness(String cardNo) {
        boolean response = clientRepository.existsIDCardNo(cardNo);
        if (response) {
            errors.add("Identity card number already exists.");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
