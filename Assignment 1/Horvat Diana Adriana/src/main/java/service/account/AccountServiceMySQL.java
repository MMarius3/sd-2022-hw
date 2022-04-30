package service.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import respository.account.AccountRepository;

import java.util.List;

public class AccountServiceMySQL implements  AccountService{

    private final AccountRepository accountRepository;
    public AccountServiceMySQL(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean addAccount(int client_id, String type, Long amount){

        Account account = new AccountBuilder()
                .setClientId(client_id)
                .setType(type)
                .setAmount(amount)
                .build();

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findByClientId(int id){
        return accountRepository.findByClientId(id);
    }

    @Override
    public boolean removeAccount(Account account){
        return accountRepository.removeAccount(account);
    }

    @Override
    public boolean updateAccount(Account account){
        return accountRepository.update(account);
    }

    @Override
    public Account findById(int id){
        return accountRepository.findById(id);
    }
}
