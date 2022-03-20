package model.validator;

import com.mysql.cj.xdevapi.Client;
import controller.Response;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private static final String PNC_VALIDATION_REGEX="[0-9]{12}";
    public static final String CARD_VALIDATION_REGEX ="\\b((4\\d{3}|5[1-5]\\d{2}|2\\d{3}|3[47]\\d{1,2})[\\s\\-]?\\d{4,6}[\\s\\-]?\\d{4,6}?([\\s\\-]\\d{3,4})?(\\d{3})?)\\b";
    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void validate(String name,String pnc,String card,String address){
        errors.clear();
        validateNameUniqueness(name);
        validateCard(card);
        validatePNC(pnc);
        validateAddress(address);

    }

    private void validateAddress(String address){
        if(address.isEmpty()){
            errors.add("Empty address");
        }
    }
    private void validateNameUniqueness(String name){
        final Response<Boolean> response = clientRepository.existsByName(name);
        if(response.hasErrors()){
            errors.add(response.getFormattedErrors());
        }else{
            final Boolean data = response.getData();
            if(data){
                errors.add("Name is already taken");
            }
        }
    }

    private void validatePNC(String pnc){
        if(!pnc.matches(PNC_VALIDATION_REGEX)){
            errors.add("PNC not valid");
        }
    }

    private void validateCard(String card){
        if(!card.matches(CARD_VALIDATION_REGEX)){
            errors.add("Card number is not valid");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
