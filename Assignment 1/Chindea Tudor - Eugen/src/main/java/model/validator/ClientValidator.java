package model.validator;
import controller.Response;
import model.Client;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
public class ClientValidator {
    private static final String CNP_VALIDATION_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private static final String NAME_VALIDATION_REGEX = "/^[A-Za-z]+$/";

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validate(String name, String CNP) {
        errors.clear();
        validateCNPUniqueness(CNP);
        validateCNP(CNP);
       // validateName(name);
    }

    private void validateCNPUniqueness(String cnp) {
        final Response<Boolean> response = clientRepository.existsByCNP(cnp);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("CNP is already in use");
            }
        }

    }

    private void validateCNP(String email) {
        if (!email.matches(CNP_VALIDATION_REGEX)) {
            errors.add("CNP is not valid");
        }
    }
//    private void validateName(String name) {
//            if (!name.matches(NAME_VALIDATION_REGEX)) {
//                errors.add("Name is not valid");
//            }
//        }
    public List<String> getErrors() {
        return errors;
    }
    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
