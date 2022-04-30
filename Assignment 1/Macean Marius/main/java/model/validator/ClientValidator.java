package model.validator;

import controller.Response;
import model.Client;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientValidator {

    public static final int MIN_ID_CARD_NUMBER_LENGTH = 4;
    public static final int MAX_ID_CARD_NUMBER_LENGTH = 8;

    public static final int MIN_ID_CODE_LENGTH = 8;
    public static final int MAX_ID_CODE_LENGTH = 13;

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validateAdd(String name, String idCardNumber, String idCode) {
        validate(name, idCardNumber, idCode);
    }

    public void validateUpdate(String id, String name, String idCardNumber, String idCode) {
        validate(name, idCardNumber, idCode);
        validateIDOnlyDigits(id);
        validateIDExistence(id);
    }

    public void validateView(String id) {
        validateIDOnlyDigits(id);
        validateIDExistence(id);
    }

    public void validate(String name, String idCardNumber, String idCode) {
        errors.clear();

        validateNameOnlyFirstName(name);
        validateNameCapitalLetter(name);
        validateNameOnlyLetters(name);
        validateIdCardNumberOnlyDigits(idCardNumber);
        validateIdCardNumberLength(idCardNumber);
        validateIdCodeOnlyDigits(idCode);
        validateIdCodeLength(idCode);
    }

    private void validateIDOnlyDigits(String id) {
        if (!id.matches("[0-9]+")) {
            errors.add("ID must only contain digits");
        }
    }

    private void validateIDExistence(String id) {
        final Optional<Client> client = clientRepository.findById(Long.getLong(id));
        if (!client.isPresent()) {
            errors.add("There is no client having this ID");
        }
    }

    private void validateNameCapitalLetter(String name) {
        if (!name.matches("^[A-Z]")) {
            errors.add("Name must start with capital letter");
        }
    }

    private void validateNameOnlyFirstName(String name) {
        if (name.matches(" ")) {
            errors.add("Write only your first name");
        }
    }

    private void validateNameOnlyLetters(String name) {
        if (!name.matches("[A-Za-z]+")) {
            errors.add("Name must only contain letters");
        }
    }

    private void validateIdCardNumberOnlyDigits(String idCardNumber) {
        if (!idCardNumber.matches("[0-9]+")) {
            errors.add("ID card number must only contain digits");
        }
    }

    private void validateIdCodeOnlyDigits(String idCode) {
        if (!idCode.matches("[0-9]+")) {
            errors.add("ID code must only contain digits");
        }
    }

    private void validateIdCardNumberLength(String idCardNumber) {
        if (idCardNumber.length() < MIN_ID_CARD_NUMBER_LENGTH || idCardNumber.length() > MAX_ID_CARD_NUMBER_LENGTH) {
            errors.add("ID card number must be between 4 and 8 digits long");
        }
    }

    private void validateIdCodeLength(String idCode) {
        if (idCode.length() < MIN_ID_CODE_LENGTH || idCode.length() > MAX_ID_CODE_LENGTH) {
            errors.add("ID code must be between 4 and 8 digits long");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}