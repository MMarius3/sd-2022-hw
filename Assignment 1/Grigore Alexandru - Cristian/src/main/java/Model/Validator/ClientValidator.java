package Model.Validator;

import Repository.Client.ClientRepository;
import Controller.Response;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private final ClientRepository clientRepository;
    private final List<String> errors = new ArrayList<>();
    private static final String CNP_REGEX = "^[1-9]\\\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\\\d|3[01])(0[1-9]|[1-4]\\\\d|5[0-2]|99)(00[1-9]|0[1-9]\\\\d|[1-9]\\\\d\\\\d)\\\\d$";

    public ClientValidator(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void validate(String name, Long identityCardNumber, String cnp, String address){
        errors.clear();
        validateName(name);
        validateIdentityCardNumber(identityCardNumber);
        validateCNP(cnp);
        validateAddress(address);
    }

    private void validateName(String name){
        if(name.isBlank()){
            errors.add("Name cannot be left incomplete");
        }
    }

    private void validateIdentityCardNumber(Long idnr){
        if(idnr.toString().length() != 16){
            errors.add("ID number incorrect");
        }
    }

    private void validateCNP(String cnp){
        if(cnp.length()!= 13){
            errors.add("CNP incorrect");
        }
    }

    private void validateAddress(String address){
        if(address.isBlank()){
            errors.add("Address incorrect");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
