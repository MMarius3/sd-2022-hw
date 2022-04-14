package model.validator;

import controller.Response;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final String CNP_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private static final String ID_REGEX = "^(?:A[RSXZ]|BV|CJ|D[DPRTXZ]|G[GLZ]|H[DR]|IF|K[LSTVXZ]|M[HMSXZ]|[NO]T|P[HX]|R[DKRTX]|S[BMVXZ]|T[CMRZ]|V[NSX]|X[BCHMRTVZ]|Z[CHLSV])\\d{6}$";

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public void validate(String name, String cnp, String idCardNumber) {
        errors.clear();
        verifyName(name);
        verifyCnpStructure(cnp);
        verifyCNPUniqueness(cnp);
        verifyIdCard(idCardNumber);
        verifyIdCardUniqueness(idCardNumber);

    }

    public void validateNameOnly(String name){
        verifyName(name);
    }
    public void validateCnpOnly(String cnp){
        verifyCnpStructure(cnp);
        verifyCnpStructure(cnp);
    }
    public void validateIdCardOnly(String idCard){
        verifyIdCard(idCard);
        verifyIdCardUniqueness(idCard);
    }
    private void verifyName(String name){
        if(name.matches("^[A-z]+[a-z]+[0-9]+$")){
            errors.add("Enter a valid name");
        }
    }
    private void verifyCnpStructure(String cnp){
        if(!cnp.matches(CNP_REGEX)){
            errors.add("CNP is not valid");
        }
    }
    private void verifyCNPUniqueness(String cnp){
        final Response<Boolean> response = clientRepository.existsByCnp(cnp);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("CNP already exists");
            }
        }
    }
    private void verifyIdCard(String idCardNumber){
        if(!idCardNumber.matches(ID_REGEX)){
            errors.add("Enter a valid id card");
        }
    }
    private void verifyIdCardUniqueness(String idCardNumber){
        final Response<Boolean> response = clientRepository.existsByIdCardNumber(idCardNumber);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("ID already exists");
            }
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
