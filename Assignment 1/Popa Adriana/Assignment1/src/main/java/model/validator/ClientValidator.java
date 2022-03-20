package model.validator;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private static final String IDENTITY_CARD_NUMBER_REGEX = "^(?:A[RSXZ]|BV|CJ|D[DPRTXZ]|G[GLZ]|H[DR]|IF|K[LSTVXZ]|M[HMSXZ]|[NO]T|P[HX]|R[DKRTX]|S[BMVXZ]|T[CMRZ]|V[NSX]|X[BCHMRTVZ]|Z[CHLSV])\\d{6}$";
    private static final String PERSONAL_NUMERIC_CODE_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";

    private final List<String> errors = new ArrayList<>();

    public ClientValidator() {
    }

    public void validate(Client client){
        errors.clear();
        validateName(client);
        validateIdentityCardNumber(client);
        validatePersonalNumericalCode(client);
    }

    private void validateName(Client client){
        if(client.getName() == null){
            errors.add("Name not Valid!");
        }
    }

    private void validateIdentityCardNumber(Client client){
        if(!client.getIdentityCardNumber().matches(IDENTITY_CARD_NUMBER_REGEX)){
            errors.add("Identity Card Number not Valid!");
        }
    }
    
    private void validatePersonalNumericalCode(Client client){
        if(!client.getPersonalNumericalCode().matches(PERSONAL_NUMERIC_CODE_REGEX)){
            errors.add("Personal Numerical Code not Valid!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
