package model.validator;

import controller.Response;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private  static final String CNP_VALIDATION_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private static final String IDCARDNO_VALIDATION_REGEX = "^\\d{5}$";
    private static final int MIN_ADDRESS_LENGTH = 5;
    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validate(String username, String cnp,String idCardNo,String address) {
        errors.clear();
        validateEmail(username);
        validateCNP(cnp);
        validateIdCardNo(idCardNo);
        validateAddress(address);
    }

    private void validateAddress(String address){
        if (!(address.length() >= MIN_ADDRESS_LENGTH)) {
            errors.add("Address must be at least 5 characters long");
        }
    }

    private void validateEmail(String email) {
        if (!email.matches(EMAIL_VALIDATION_REGEX)) {
            errors.add("Email is not valid");
        }
    }

    private void validateCNP(String cnp){
        if(!cnp.matches(CNP_VALIDATION_REGEX)){
            errors.add("CNP is not VALID");
        }
    }

    private void validateIdCardNo(String idCardNo) {
        if (!idCardNo.matches(IDCARDNO_VALIDATION_REGEX)) {
            errors.add("Card id number not valid");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
