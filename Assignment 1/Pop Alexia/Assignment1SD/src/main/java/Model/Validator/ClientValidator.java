package Model.Validator;

import Controller.Response;
import Repository.Client.ClientRepository;
import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final String NAME_VALIDATION_REGEX = "^[a-zA-Z]+$";
    private static final String ADDRESS_VALIDATION_REGEX = "^([0-9]*,Str\\.?[a-zA-Z]*|[a-zA-Z]*)";
    public static final int CARD_NR_LENGTH = 3;
    public static final int PNC_NR_LENGTH = 5;
    public static final String ONLY_NR_REGEX ="^[0-9]*$";

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validateUpdate(String id , String name,String pnc,String address){
        errors.clear();
        if(id.equals(""))
            errors.add("Enter a client ID");
        else if(!id.matches(ONLY_NR_REGEX))
            errors.add("ID must be a number");
        else if(clientRepository.findById(Long.parseLong(id)) == null)
            errors.add("Client with this ID does not exist");
        else if(name.equals("") && address.equals("") && pnc.equals("-1"))
            errors.add("Enter a name, address or PNC to update");
        else {
            if (name.equals("") && pnc.equals("-1")) {
                validateAddress(address);
            } else if (name.equals("") && address.equals("")) {
                validatePnc(pnc);
                validatePNCUniqueness(Long.parseLong(pnc));
            } else if(pnc.equals("") && address.equals("")){
                validateName(name);
            } else if (pnc.equals("-1")) {
                validateAddress(address);
                validateName(name);
            } else if (name.equals("")){
                validateAddress(address);
                validatePnc(pnc);
                validatePNCUniqueness(Long.parseLong(pnc));
            } else if (address.equals("")){
                validateName(name);
                validatePnc(pnc);
                validatePNCUniqueness(Long.parseLong(pnc));
            }
        }
    }

    public void validate(String cardNr, String name, String pnc, String address) {
        errors.clear();
        if(cardNr.equals("") || name.equals("") || pnc.equals("") || address.equals(""))
            errors.add("Enter a card number ,name,pnc and address to add a client");
        else {
            validateCardNrDigits(cardNr);
            validateCardNrLength(cardNr);
            validateName(name);
            validatePnc(pnc);
            validateAddress(address);
            if(errors.isEmpty()) {
                validateCardNrUniqueness(Long.parseLong(cardNr));
                validatePNCUniqueness(Long.parseLong(pnc));
            }
        }
    }

    private void validateCardNrUniqueness(Long cardnr) {
        final Response<Boolean> response = clientRepository.existsByCardnr(cardnr);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Card number is already taken");
            }
        }
    }
    private void validateCardNrDigits(String cardnr) {
        if (!cardnr.matches(ONLY_NR_REGEX)) {
            errors.add("Card number must contain only numbers");
        }
    }

    private void validateCardNrLength(String cardnr) {
        if (!(cardnr.length() == CARD_NR_LENGTH)) {
            errors.add("Card number must have 3 numbers");
        }
    }
    private void validatePnc(String pnc){
        if (!(pnc.length() == PNC_NR_LENGTH) || !pnc.matches(ONLY_NR_REGEX)) {
            errors.add("PNC number must have 5 numbers");
        }
    }

    private void validateName(String name) {
        if (!name.matches(NAME_VALIDATION_REGEX)) {
            errors.add("Name must contain only letters");
        }
    }

    private void validateAddress(String address){
        if(!address.matches(ADDRESS_VALIDATION_REGEX))
            errors.add("Invalid address");
    }

    private void validatePNCUniqueness(Long pnc) {
        final Response<Boolean> response = clientRepository.existsByPNC(pnc);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("PNC exists already");
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
