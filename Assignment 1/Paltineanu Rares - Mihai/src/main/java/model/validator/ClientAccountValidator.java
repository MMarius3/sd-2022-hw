package model.validator;

import controller.Response;
import model.Client;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientAccountValidator {

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientAccountValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validate(String client_id, String number, String type, String money) {
        errors.clear();
        validateClientId(client_id);
        validateMoney(money);
        validateNumber(number);
    }

    private void validateNumber(String number) {
        if (!number.matches("^(\\d{16})?$")) {
            errors.add("Card number must contain 16 digits");
        }
    }

    private void validateClientId(String client_id) {
        boolean isClientIdNumber = isClientIdNumber(client_id);
        if(isClientIdNumber) {
            doesClientExist(Long.parseLong(client_id));
        }
    }

    private boolean isClientIdNumber(String client_id) {
        try {
            Long.parseLong(client_id);
            return true;
        } catch (NumberFormatException ex) {
            errors.add("Id must be integer");
            return false;
        }
    }

    private void doesClientExist(Long client_id) {
        Client client = clientRepository.findById(client_id);
        if(client == null) {
            errors.add("Client with id " + client_id + " does not exist");
        }
    }

    private void validateMoney(String money) {
        boolean isMoneyNumber = isMoneyNumber(money);
        if(isMoneyNumber) {
            isMoneyPositive(Integer.parseInt(money));
        }
    }

    private boolean isMoneyNumber(String money) {
        try {
            Integer.parseInt(money);
            return true;
        } catch (NumberFormatException ex) {
            errors.add("Money must be integer");
            return false;
        }
    }

    private void isMoneyPositive(int money) {
        if(money < 0) {
            errors.add("Money must be positive");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
