package model.validator;

import controller.Response;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientAccountValidator {

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;

    public ClientAccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validate(int identificationNumber, String type, int money) {

    }

    public void validateIdentificationNumberUniqueness(String username) {
//        final Response<Boolean> response = clientRepository.existsByUsername(username);
//        if (response.hasErrors()) {
//            errors.add(response.getFormattedErrors());
//        } else {
//            final Boolean data = response.getData();
//            if (data) {
//                errors.add("Email is already taken");
//            }
//        }
    }
}
