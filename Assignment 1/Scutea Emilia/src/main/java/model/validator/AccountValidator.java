package model.validator;


import controller.Response;
import database.Constants;
import repository.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private final List<String> errors = new ArrayList<>();
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    public void validateAdd(String type, Double amount){
        errors.clear();
        validateType(type);
        validateAmount(amount);
    }

    public void validateTransfer(Long fromId, Long toId, Double amount){
        errors.clear();
        validateAccountId(fromId);
        validateAccountId(toId);
        validateAmount(amount);
        validateEnoughAmountInAccount(fromId, amount);
    }

    public void validateUpdate(String type, Long accountId){
        errors.clear();
        validateType(type);
        validateAccountId(accountId);
    }

    public void validatePayBill(Long fromId, Double amount){
        errors.clear();
        validateAccountId(fromId);
        validateEnoughAmountInAccount(fromId, amount);
    }

    private void validateEnoughAmountInAccount(Long fromId, Double amount){
        Double accountAmount = accountRepository.selectAmount(fromId);
        if(accountAmount != null){
            if(amount > accountAmount){
                errors.add("Not enough money in account");
            }
        }
    }

    private void validateAccountId(Long id){
        final Response<Boolean> response = accountRepository.existsById(id);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (!data) {
                errors.add("Client with id %d  does not exist".formatted(id));
            }
        }
    }

    private void validateType(String type){
        if(!type.equalsIgnoreCase(Constants.AccountTypes.CREDIT) && !type.equalsIgnoreCase(Constants.AccountTypes.DEBIT)){
            errors.add("Account must be of type debit or credit.");
        }
    }

    private void validateAmount(Double amount){
        if(amount < 0){
            errors.add("Amount must be positive");
        }
    }

    public List<String> getErrors(){
        return errors;
    }

    public String getFormattedErrors(){
        return String.join("\n", errors);
    }
}
