package Model.Validator;

import Controller.Response;
import Repository.Account.AccountRepository;
import Repository.Client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

import static Database.Constants.AccountType.INVESTMENT;
import static Database.Constants.AccountType.SAVINGS;

public class AccountValidator {

    public static final int CARD_NR_LENGTH = 3;
    public static final String CARD_NR_REGEX ="^[0-9]*$";
    public static final String ONLY_NR_REGEX ="^[0-9]*$";

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountValidator(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public void validateUpdate(String accNr,String type,String amount){
        errors.clear();
        if(accNr.equals(""))
            errors.add("Enter an account number");
        else if(!accNr.matches(ONLY_NR_REGEX))
            errors.add("Account number must contain only numbers");
        else if(accountRepository.findByCardNr(Long.parseLong(accNr)) == null)
            errors.add("Account with this number does not exist");
        else if(type.equals("") && amount.equals("-1"))
            errors.add("Enter a type and/or amount to update");
        else{
            if(type.equals("")) validateAmount(amount);
            else if(amount.equals("-1")) validateType(type);
            else{
                validateAmount(amount);
                validateType(type);
            }
        }
    }

    public void validateDelete(String accNr){
        errors.clear();
        if(accNr.equals(""))
            errors.add("Input an account number");
        else {
            validateCardNrLenght(accNr);
            validateCardNrDigits(accNr);
            if(errors.isEmpty()) {
                if (accountRepository.findByCardNr(Long.parseLong(accNr)) == null)
                    errors.add("Account with this number does not exist");
            }
        }
    }

    public void validateTransaction(String cardnr1,String cardnr2,String amount){
        errors.clear();
        if(cardnr1.equals("") || cardnr2.equals(""))
            errors.add("Enter two account numbers");
        else {
            validateCardNrDigits(cardnr1);
            validateCardNrDigits(cardnr2);
            validateCardNrLenght(cardnr1);
            validateCardNrLenght(cardnr2);
            validateAmount(amount);
        }
        if(errors.isEmpty()) {
            if (accountRepository.findByCardNr(Long.parseLong(cardnr1)) == null)
                errors.add("First account does not exist");
            if (accountRepository.findByCardNr(Long.parseLong(cardnr2)) == null)
                errors.add("Second account does not exist");
            else if (accountRepository.findByCardNr(Long.parseLong(cardnr1)).getAmount() - Integer.parseInt(amount) < 0)
                errors.add("Amount greater than sum in account");
        }
    }

    public void validatePayment(String cardNr,String amount){
        errors.clear();
        if(cardNr.equals(""))
            errors.add("Enter an account number");
        else {
            validateCardNrDigits(cardNr);
            validateCardNrLenght(cardNr);
            validateAmount(amount);
        }

        if(errors.isEmpty()) {
            if (accountRepository.findByCardNr(Long.parseLong(cardNr)) == null)
                errors.add("Account does not exist");
            else if(accountRepository.findByCardNr(Long.parseLong(cardNr)).getAmount() - Integer.parseInt(amount)<0)
                errors.add("Insuficient funds");
        }
    }

    public void validate(String cardNr,String type,String amount) {
        errors.clear();
        if(cardNr.equals("") || type.equals("") || amount.equals(""))
            errors.add("Input an account number , type and amount to add an account");
        else {
            validateCardNrLenght(cardNr);
            validateCardNrDigits(cardNr);
            validateAmount(amount);
            validateType(type);

            if(errors.isEmpty()) {
                validateCardNrUniqueness(cardNr);
                if(clientRepository.findByCardNr(Long.parseLong(cardNr)) == null)
                    errors.add("Create a client before creating an account");
            }
        }
    }

    private void validateCardNrUniqueness(String cardnr) {
        final Response<Boolean> response = accountRepository.existsByCardnr(Long.parseLong(cardnr));
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
        if (!cardnr.matches(CARD_NR_REGEX)) {
            errors.add("Card number must contain only numbers");
        }
    }

    private void validateCardNrLenght(String cardnr) {
        if (!(cardnr.length() == CARD_NR_LENGTH)) {
            errors.add("Card number must have 3 numbers");
        }
    }

    private void validateType(String type) {
        if (!type.matches(SAVINGS) && !type.matches(INVESTMENT)) {
            System.out.println(type);
            errors.add("Account type is not valid");
        }
    }

    private void validateAmount(String amount){
        if(!String.valueOf(amount).matches(ONLY_NR_REGEX))
            errors.add("Amount must be a positive number");
        else if(Integer.parseInt(amount)<0)
            errors.add("Amount must be a positive number");
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
