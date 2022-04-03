package model.validator;

import controller.Response;
import model.Client;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    //private static final String PNC_VALIDATION_REGEX = "^[1-9]\\\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\\\d|3[01])(0[1-9]|[1-4]\\\\d|5[0-2]|99)(00[1-9]|0[1-9]\\\\d|[1-9]\\\\d\\\\d)\\\\d$";
    //private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public void validate(Client client) {
        errors.clear();
        validatePNC(client.getPnc());
        validatePNCUniqueness(client.getPnc());
        validatePNCLength(client.getPnc());
        validateIdCardNumber(client.getIdCardNumber());
        validateIDCardNumberLength(client.getIdCardNumber());
    }

    private void validateIdCardNumber(String idCardNumber) {
        if(!isNumeric(idCardNumber)){
            errors.add("ID card number must contain only digits!");
        }
    }

    private void validateIDCardNumberLength(String idCardNumber) {
        if(idCardNumber.length()!=5){
            errors.add("ID card number must be 5 characters long!");
        }
    }

    private void validatePNCUniqueness(String pnc) {
        final Response<Boolean> response = clientRepository.existsByPNC(pnc);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("There is already one client with this pnc!");
            }
        }
    }
    private void validatePNCLength(String pnc){
        if(pnc.length()!=13){
            errors.add("PNC must be 13 characters long!");
        }
    }
    private static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    private void validatePNC(String pnc){
        if(!isNumeric(pnc)){
            errors.add("PNC must contain only digits!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
