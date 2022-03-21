package model.validator;

import controller.Response;
import respository.client.ClientRepository;
import respository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String PNC_VALIDATION_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void validate(String name, String idCardNr, String PNC, String address, String email) {
        errors.clear();
        Long idCardNrLong = validateidCardNr(idCardNr);
        Long PNCLong = validatePNC(PNC);
        validateIdCardNrUniqueness(idCardNrLong);
        validatePNCUniqueness(PNCLong);
        validateEmail(email);
        validateEmailUniqueness(email);

    }

    public void validateUpdate(String idCardNr, String PNC, String email){
        errors.clear();
        validateidCardNr(idCardNr);
        validatePNC(PNC);
        validateEmail(email);
    }

    public Long validatePNC(String PNC){
        try{
            if (!PNC.matches(PNC_VALIDATION_REGEX)) {
                errors.add("Personal numerical code is not valid");
                return null;
            }
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
            errors.add("Identification card number is not valid");
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
