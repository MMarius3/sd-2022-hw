package model.validator;

import repository.account.AccountRepository;


import java.util.ArrayList;
import java.util.List;

public class TransactionValidator {

    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;

    public TransactionValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validateTransfer(String from, String to, String sum){
        errors.clear();
        validateEmpty(from,to,sum);
        if(errors.isEmpty()){
           validateTransferValues(from,to,sum);
            validateEquality(from,to);
        }
        if(errors.isEmpty()){
            validateId(from);
            validateId(to);
        }
        if(errors.isEmpty()){
            float floatSum=Float.parseFloat(sum);
            Long fromId=Long.parseLong(from);
            validateSum(floatSum, fromId);
        }
    }

    public void validateBill(String id, String sum, String description) {
        errors.clear();
        validateEmpty(id,sum,description);
        if(errors.isEmpty()){
            validateBillValues(id,sum);
        }
        if(errors.isEmpty()){
            validateId(id);
        }
        if(errors.isEmpty()){
            float floatSum=Float.parseFloat(sum);
            Long accountId=Long.parseLong(id);
            validateSum(floatSum, accountId);
        }
    }

    private void validateSum(float sum, Long from) {
        if(!accountRepository.validSum(sum,from)){
            errors.add("Not enough money in account to perform the action!");
        }
    }

    private void validateEquality(String from, String to) {
        if(from.equals(to)){
            errors.add("Please insert different accounts!");
        }
    }

    private void validateId(String id) {
        if(!accountRepository.existsAccountId(id)){
            errors.add("Account ID is invalid!");
        }
    }

    private void validateEmpty(String s1, String s2, String s3){
        if(s1.equals("")||s2.equals("")||s3.equals("")){
            errors.add("Make sure you complete all fields!");
        }
    }

    private void validateTransferValues(String from, String to, String sum){
        try{
            Long fromId=Long.parseLong(from);
            Long toId=Long.parseLong(to);
            float floatSum=Float.parseFloat(sum);
        }catch(NumberFormatException exception){
            errors.add("Please insert numerical values!");
        }
    }

    private void validateBillValues(String stringId, String sum){
        try{
            Long id=Long.parseLong(stringId);
            float floatSum=Float.parseFloat(sum);
        }catch(NumberFormatException exception){
            errors.add("Please insert numerical values!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }


}
