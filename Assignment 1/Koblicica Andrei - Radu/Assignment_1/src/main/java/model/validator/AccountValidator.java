package model.validator;

import controller.Response;
import model.Account;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    public static final String IDENTIFICATION_NUMBER_REGEX="\\b\\d{4}[ -]?\\d{4}[ -]?\\d{4}[ -]?\\d{4}\\b";//just checking if it has 16 digits

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }





    public void validate(String clientId, String identificationNumber, String type, String balance, String date, boolean checkIdentificationNumber) {
        errors.clear();
        validateEmpty(clientId,identificationNumber,type,balance,date);
        if(errors.isEmpty()){
            validateClientId(clientId);
            validateClientIdPresence(clientId);
            validateBalance(balance);
            if(checkIdentificationNumber){
                validateIdentificationNumber(identificationNumber);
                validateIdentificationNumberUniqueness(identificationNumber);
            }
        }

    }


    private void validateBalance(String balance) {
        try{
            float newBalance=Float.parseFloat(balance);
        }catch(NumberFormatException e){
            errors.add("Balance is not a float value!");
        }
    }

    private void validateClientIdPresence(String clientId) {
        if(!accountRepository.existsClientId(clientId)){
            errors.add("Client ID is invalid!");
        }
    }

    private void validateClientId(String clientId) {
        try{
            Long newClientId=Long.parseLong(clientId);
        }catch(NumberFormatException e){
            errors.add("Client ID is not a numeric value!");
        }
    }

    public void validateEmpty(String clientId, String identificationNumber, String type, String balance, String date){
        if(clientId.equals("")||identificationNumber.equals("")||type.equals("")||balance.equals("")||date.equals("")){
            errors.add("Make sure you complete all fields!");
        }
    }

    private void validateIdentificationNumber(String identificationNumber) {
        if(!identificationNumber.matches(IDENTIFICATION_NUMBER_REGEX)){
            errors.add("Identification number is not valid!");
        }
    }

    private void validateIdentificationNumberUniqueness(String identificationNumber){
        final Response<Boolean> response = accountRepository.existsIdentificationNumber(identificationNumber);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Identification number already exists!");
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
