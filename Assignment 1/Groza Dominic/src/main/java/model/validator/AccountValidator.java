package model.validator;

import controller.Response;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final List<String> errors = new ArrayList<>();

    public AccountValidator(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    public void validate(String accountType, Long accountBalance, Long clientId) {
        errors.clear();
        validateLength(accountType, "Account type");
        validateBalance(accountBalance);
        validateClientId(clientId);
    }

    public void validateForUpdate(String accountType, Long accountBalance, Long clientId) {
        errors.clear();
        validateLength(accountType, "Account type");
        validateBalance(accountBalance);
    }

    private void validateLength(String string, String slug) {
        if (string.length() < 2) {
            errors.add(slug + " length is not correct");
        }
    }

    private void validateBalance(Long balance) {
        if (balance < 0) {
            errors.add("This operation results in balance falling below 0");
        }
    }

    private void validateClientId(Long clientId) {
        final Response<Boolean> response = accountRepository.existsByCustomerId(clientId);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("This client can already have one account!");
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
