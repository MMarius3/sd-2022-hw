package model.validator;

import repository.client.ClientRepository;
import repository.user.UserRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final String CNP_VALIDATION_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private static final Integer MAX_LENGTH = 100;
    private static final String ID_NUMBER_REGEX = "^[A-Z]{2}[0-9]{6}$";

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void validate(String name, String cnp, String idNumber, String address){
        errors.clear();
        validateName(name);
        validateCnp(cnp);
        validateIdNumber(idNumber);
        validateAddress(address);
    }

    private void validateName(String name){
        if(name.equals("") || name == null){
            errors.add("Name must be filled");
        }
        else if(name.length() > MAX_LENGTH){
            errors.add("Name is too long");
        }
    }

    private void validateCnp(String cnp){
        if(cnp.equals("") || cnp == null){
            errors.add("Cnp must be filled");
        }
        else if(!cnp.matches(CNP_VALIDATION_REGEX)){
            errors.add("CNP is invalid");
        }
    }

    private void validateIdNumber(String idNumber){
        if(idNumber.equals("") || idNumber == null){
            errors.add("idNumber must be filled");
        }
        else if(!idNumber.matches(ID_NUMBER_REGEX)){
            errors.add("IdNumber is invalid");
        }
    }

    private void validateAddress(String address){
        if(address.equals("") || address == null){
            errors.add("address must be filled");
        }
        else if(address.length() > MAX_LENGTH){
            errors.add("Address is too long");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
