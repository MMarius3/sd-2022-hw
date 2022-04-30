package model.validator;

import controller.Response;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private final ClientRepository clientRepository;
    private final List<String> errors = new ArrayList<>();

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public void validate(String name, String id_card_number, String personal_numerical_code, String address) {
        validateLength(name, "Name");
        validateLength(id_card_number, "ID-Card number");
        validateLength(personal_numerical_code, "Personal numerical code");
        validateLength(personal_numerical_code, "Address");
        validateIdCardNumber(id_card_number);
        validateCNP(personal_numerical_code);
    }

    public void validateForUpdate(String name, String id_card_number, String personal_numerical_code, String address) {
        errors.clear();
        validateLength(name, "Name");
        validateLength(id_card_number, "ID-Card number");
        validateLength(personal_numerical_code, "Personal numerical code");
        validateLength(personal_numerical_code, "Address");
    }

    private void validateIdCardNumber(String card_number) {
        char[] ch = card_number.toCharArray();
        for (char c : ch) {
            if (Character.isLetter(c)) {
                errors.add("ID-Card number is not correct");
                break;
            }
        }
    }

    private void validateCNP(String cnp) {
        if (!cnp.matches("^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$")) {
            errors.add("Personal numerical code is not correct");
        } else {
            final Response<Boolean> response = clientRepository.existsByNumericalCode(cnp);
            if (response.hasErrors()) {
                System.out.println("asdasidia");
                errors.add(response.getFormattedErrors());
            } else {
                final Boolean data = response.getData();
                if (data) {
                    errors.add("Client with this personal numerical code already exists");
                }
            }
        }
    }

    private void validateLength(String string, String slug) {
        if (string.length() < 2) {
            errors.add(slug + " length is not correct");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
