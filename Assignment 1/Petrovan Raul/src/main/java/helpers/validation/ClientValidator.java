package helpers.validation;

import model.Client;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {
    private static final String CNP_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private static final String idNumber_REGEX = "[A-Z]{2}\\d{6}";

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
        UserValidator userValidator = new UserValidator(client.getUser());
        userValidator.validate();

        validateCNP();
        validateIdNumber();

        errors.addAll(userValidator.getErrors());


        return errors.isEmpty();
    }

    private void validateIdNumber() {
        if (!Pattern.compile(idNumber_REGEX).matcher(client.getIdNumber()).matches()) {
            errors.add("Invalid ID Number!");
        }
    }

    private void validateCNP() {
        if (!Pattern.compile(CNP_REGEX).matcher(client.getCNP()).matches()) {
            errors.add("Invalid CNP!");
        }
    }
}
