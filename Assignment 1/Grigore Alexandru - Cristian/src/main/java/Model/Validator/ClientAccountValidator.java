package Model.Validator;

import Repository.ClientAccount.ClientAccountRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientAccountValidator {

    private final ClientAccountRepository clientAccountRepository;
    private final List<String> errors = new ArrayList<>();

    public ClientAccountValidator(ClientAccountRepository clientAccountRepository){
        this.clientAccountRepository = clientAccountRepository;
    }

    public void validate(Long identificationNumber, String type, int amount){
        errors.clear();
        validateIdentificationNumber(identificationNumber);
        validateType(type);
        validateAmount(amount);
    }

    private void validateIdentificationNumber(Long id){
        if(id.toString().length()!=16){
            errors.add("Incorrect card number");
        }
    }

    private void validateType(String type){
        if(!(type.equals("Visa") || type.equals("Mastercard"))){
            errors.add("Nota valid card type");
        }
    }

    private void validateAmount(int amount){
        if(amount <= 0){
            errors.add("Cannot create account with 0 or less money");
        }
    }
    
    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
