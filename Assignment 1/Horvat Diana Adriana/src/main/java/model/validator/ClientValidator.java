package model.validator;

import controller.Response;
import respository.client.ClientRepository;
import respository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void validate(String name, String idCardNr, String PNC, String address, String email) {
        errors.clear();
        Long idCardNrLong = validateidCardNr(idCardNr);
        Long PNCLong = validatePNC(PNC);
        if(idCardNrLong == null || PNCLong == null){
            errors.add("Identification card number or personal numerical code is not valid");
        }else{
            validateIdCardNrUniqueness(idCardNrLong);
            validatePNCUniqueness(PNCLong);
            validateEmail(email);
            validateEmailUniqueness(email);
        }

    }

    public void validateUpdate(String idCardNr, String PNC, String email){
        errors.clear();
        Long idCardNrLong = validateidCardNr(idCardNr);
        Long PNCLong = validatePNC(PNC);
        if(idCardNrLong == null || PNCLong == null){
            errors.add("Identification card number or personal numerical code is not valid");
        }else{
            this.validateEmail(email);
        }
    }

    public Long validatePNC(String PNC){
        try{
            Long PNCLong = Long.parseLong(PNC);
            return PNCLong;
        }catch(NumberFormatException e){
            return null;
        }
    }

    public Long validateidCardNr(String idCardNr){
        try{
            Long idCardNrLong = Long.parseLong(idCardNr);
            return idCardNrLong;
        }catch(NumberFormatException e){
            return null;
        }
    }

    private void validatePNCUniqueness(Long PNC) {
        final Response<Boolean> response = clientRepository.existsByPNC(PNC);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Personal numerical code is already in the database");
            }
        }
    }

    private void validateIdCardNrUniqueness(Long idCardNr) {
        final Response<Boolean> response = clientRepository.existsByIdCardNr(idCardNr);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Identification card number is already in the database");
            }
        }
    }

    private void validateEmailUniqueness(String email) {
        final Response<Boolean> response = clientRepository.existsByEmail(email);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Email is already taken");
            }
        }
    }

    private void validateEmail(String email) {
        if (!email.matches(EMAIL_VALIDATION_REGEX)) {
            errors.add("Email is not valid");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
