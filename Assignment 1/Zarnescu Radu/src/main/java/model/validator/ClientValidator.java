package model.validator;

import controller.Response;
import model.Client;
import services.client.ClientService;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private final List<String> errors = new ArrayList<>();
    private final ClientService clientService;

    private static final String CARDNR_VALIDATION_REGEX = "^(\\d{6})?$";
    private static final String PNC_VALIDATION_REGEX = "^(\\d{13})?$";

    public ClientValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    public void validate(String cardNr, String pnc) {
        errors.clear();
        validateCardNrUniqueness(cardNr);
        validateCardNr(cardNr);
        validatePncUniqueness(pnc);
        validatePnc(pnc);
    }

    private void validateCardNrUniqueness(String cardNr) {
        final Response<Boolean> response = clientService.existsByCardNr(cardNr);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Card number already in use");
            }
        }
    }

    private void validateCardNr(String cardNr) {
        if (!cardNr.matches(CARDNR_VALIDATION_REGEX)) {
            errors.add("Card number is not valid");
        }
    }

    private void validatePncUniqueness(String pnc) {
        final Response<Boolean> response = clientService.existsByPnc(pnc);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("PNC already in use");
            }
        }
    }

    private void validatePnc(String pnc) {
        if (!pnc.matches(PNC_VALIDATION_REGEX)) {
            errors.add("PNC is not valid");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
