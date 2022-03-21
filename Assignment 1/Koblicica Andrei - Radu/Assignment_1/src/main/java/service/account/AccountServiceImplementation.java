package service.account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;
import model.Client;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImplementation implements AccountService{
    private final AccountRepository repository;

    public AccountServiceImplementation(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public ObservableList<Account> findAll() {
        ObservableList<Account> accounts= FXCollections.observableArrayList();
        for(Account account: repository.findAll()){
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account with id %d not found".formatted(id)));
    }

    @Override
    public boolean save(Account account) {
        return repository.save(account);
    }

    @Override
    public boolean remove(Account account){
        return repository.remove(account);
    }

    @Override
    public boolean edit(Account account) {
        return repository.edit(account);
    }

    @Override
    public void updateAmount(float amount, Long id) {
        repository.updateAmount(amount,id);
    }
}
