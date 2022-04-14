package model.validator;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private final String CNP_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private final int ID_NUMBER_LENGTH = 6;
    private final String ID_NUMBER_REGEX = "\\d+";

    private final List<String> errors = new ArrayList<>();

    public ClientValidator() {
    }

    public void validate(String cnp, String idNumber) {
        errors.clear();
        validateCNP(cnp);
        validateIdNumberLength(idNumber);
        validateIdNumberContents(idNumber);
    }

    private void validateCNP(String cnp) {
        if (!cnp.matches(CNP_REGEX)) {
            errors.add("CNP is not valid");
        }
    }

    private void validateIdNumberLength(String idNumber){
        if(idNumber.length() != ID_NUMBER_LENGTH){
            errors.add("ID Number length invalid");
        }
    }

    private void validateIdNumberContents(String idNumber){
        if(!idNumber.matches(ID_NUMBER_REGEX)){
            errors.add("ID Number must be formed by digits only");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
