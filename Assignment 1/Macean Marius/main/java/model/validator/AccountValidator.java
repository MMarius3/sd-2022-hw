package model.validator;

import model.Account;
import model.Client;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountValidator {

    public static final int MAX_MONEY_AMOUNT_LENGTH = 4;
    public static final int MAX_TRANSFER_LENGTH = 2;
    public static final int MAX_BILL_LENGTH = 3;

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountValidator(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public void validateAdd(String type, String moneyAmount, String id) {
        validate(type, moneyAmount);
        validateIDOnlyDigits(id);
        validateClientIDExistence(id);
    }

    public void validateUpdate(String id, String type, String moneyAmount) {
        validate(type, moneyAmount);
        validateIDOnlyDigits(id);
        validateIDExistence(id);
    }

    public void validateView(String id) {
        validateIDOnlyDigits(id);
        validateIDExistence(id);
    }

    public void validateDelete(String id) {
        validateView(id);
    }

    public void validateTransfer(String id1, String id2, String moneyAmountToTransfer) {
        validateIDOnlyDigits(id1);
        validateIDExistence(id1);
        validateIDOnlyDigits(id2);
        validateIDExistence(id2);
        validateTransferOnlyDigits(moneyAmountToTransfer);
        validateTransferAmount(moneyAmountToTransfer);
    }

    public void validateBill(String id, String billToExecute) {
        validateIDOnlyDigits(id);
        validateIDExistence(id);
        validateBillOnlyDigits(billToExecute);
        validateBillAmount(billToExecute);
    }

    public void validate(String type, String moneyAmount) {
        errors.clear();

        validateTypeLetters(type);
        validateMoneyAmountOnlyDigits(moneyAmount);
        validateMoneyAmountLength(moneyAmount);
    }

    public void validateTransferOnlyDigits(String moneyAmount) {
        if (!moneyAmount.matches("[0-9]+")) {
            errors.add("Money amount to transfer must only contain digits");
        }
    }

    public void validateBillOnlyDigits(String billToExecute) {
        if (!billToExecute.matches("[0-9]+")) {
            errors.add("Bill must only contain digits");
        }
    }

    public void validateTransferAmount(String moneyAmount) {
        if (moneyAmount.length() > MAX_TRANSFER_LENGTH) {
            errors.add("Maximum money amount to transfer is 99");
        }
    }

    public void validateBillAmount(String billToExecute) {
        if (billToExecute.length() > MAX_BILL_LENGTH) {
            errors.add("Maximum bill to execute is 999");
        }
    }

    private void validateIDOnlyDigits(String id) {
        if (!id.matches("[0-9]+")) {
            errors.add("ID must only contain digits");
        }
    }

    private void validateIDExistence(String id) {
        final Optional<Account> account = accountRepository.findById(Long.getLong(id));
        if (!account.isPresent()) {
            errors.add("There is no account having this ID");
        }
    }

    private void validateClientIDExistence(String id) {
        final Optional<Client> client = clientRepository.findById(Long.getLong(id));
        if (!client.isPresent()) {
            errors.add("There is no client having this ID");
        }
    }

    private void validateTypeLetters(String name) {
        if (!name.matches("[a-z]+")) {
            errors.add("Type must contain only lowercase letters");
        }
    }

    private void validateMoneyAmountOnlyDigits(String moneyAmount) {
        if (!moneyAmount.matches("[0-9]+")) {
            errors.add("Money amount must only contain digits");
        }
    }

    private void validateMoneyAmountLength(String moneyAmount) {
        if (moneyAmount.length() > MAX_MONEY_AMOUNT_LENGTH) {
            errors.add("Maximum money amount in an account is 9999");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}