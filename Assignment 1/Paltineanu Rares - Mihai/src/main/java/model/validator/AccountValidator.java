package model.validator;

import controller.Response;
import model.Account;
import model.Client;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public AccountValidator(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    public void validate(String accountId, String money) {
        errors.clear();
        boolean validateAccountId = validateAccountId(accountId);
        boolean validateMoney = validateMoney(money);

        if(validateAccountId && validateMoney) {
            areEnoughMoney(Long.parseLong(accountId), Integer.parseInt(money));
        }
    }
    public void validate(String fromAccountId, String toAccountId, String money) {
        errors.clear();
        boolean validateMoney = validateMoney(money);
        boolean validateFromAccountId = validateAccountId(fromAccountId);
        boolean validateToAccountId = validateAccountId(toAccountId);

        if(validateMoney && validateFromAccountId && validateToAccountId) {
            areEnoughMoney(Long.parseLong(fromAccountId), Integer.parseInt(money));
        }
    }

    public void validate(String client_id, String number, String money, boolean verifyCardUniqueness) {
        errors.clear();
        validateClientId(client_id);
        validateMoney(money);
        validateNumber(number);
        if(verifyCardUniqueness) {
            existsByCardNumber(number);
        }
    }

    private void existsByCardNumber(String number) {
        Account account = accountRepository.findByNumber(number);
        if(account != null) {
            errors.add("Account with card number " + number + " already exists");
        }
    }
    private boolean validateAccountId(String accountId) {
        boolean isAccountIdNumber = isAccountIdNumber(accountId);
        if(isAccountIdNumber) {
            return doesAccountExist(Long.parseLong(accountId));
        }
        return false;
    }

    private boolean isAccountIdNumber(String accountId) {
        try {
            Integer.parseInt(accountId);
            return true;
        } catch (NumberFormatException ex) {
            errors.add("Account id must be integer");
            return false;
        }
    }

    private void areEnoughMoney(Long fromAccountId, int money) {
        Account account = accountRepository.findById(fromAccountId);
        if(account.getMoney() < money) {
            errors.add("Account with id " + fromAccountId + " does not have enough money");
        }
    }

    private boolean doesAccountExist(Long id) {
        Account account = accountRepository.findById(id);
        if(account == null) {
            errors.add("Account with id " + id + " does not exist");
            return false;
        }
        return true;
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

    private boolean validateMoney(String money) {
        boolean isMoneyNumber = isMoneyNumber(money);
        if(isMoneyNumber) {
            return isMoneyPositive(Integer.parseInt(money));
        }
        return false;
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

    private boolean isMoneyPositive(int money) {
        if(money < 0) {
            errors.add("Money must be positive");
            return false;
        }
        return true;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
