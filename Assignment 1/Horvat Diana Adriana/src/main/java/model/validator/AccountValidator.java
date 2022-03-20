package model.validator;

import respository.account.AccountRepository;
import respository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    private final List<String> errors = new ArrayList<>();

    private final ClientRepository clientRepository;

    public AccountValidator(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void validate(String client_id_str, String amount) {
        errors.clear();
        int client_id = validateClientOrAccountId(client_id_str);
        validateClientIdExistence(client_id);
        validateAmount(amount);

    }

    public int validateClientOrAccountId(String client_id){
        try{
            int client_id_int = Integer.parseInt(client_id);
            return client_id_int;
        }catch(NumberFormatException e){
            errors.add("Client id is not valid");
            return -1;
        }
    }

    public void validateClientIdExistence(int client_id){

        if(!clientRepository.existsById(client_id)){
            errors.add("Client id does not exist");
        }
    }

    public void validateAmount(String amount){
        try{
            Long amount_long = Long.parseLong(amount);
        }catch(NumberFormatException e){
            errors.add("Amount is not valid");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
