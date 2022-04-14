package service.account;

import model.Account;
import model.Client;
import repository.account.AccountRepository;

import java.util.Date;
import java.util.List;

public class AccountServiceMySQL implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean add(Long balance, String type, Date dateOfCreation, Long client_id) {
        Account account = new Account();
        account.setBalance(balance);
        account.setType(type);
        account.setDateOfCreation(dateOfCreation);
        account.setClient_id(client_id);

        return accountRepository.save(account);
    }

    @Override
    public boolean delete(Long id) {
        return accountRepository.removeById(id);
    }

    @Override
    public List<Account> view() {
        return accountRepository.findAll();
    }

    @Override
    public boolean update(Long id, Long balance, String type, Date dateOfCreation, Long client_id) {
        Account account = new Account();
        account.setId(id);
        account.setBalance(balance);
        account.setType(type);
        account.setDateOfCreation(dateOfCreation);
        account.setClient_id(client_id);

        return accountRepository.update(account);
    }

    @Override
    public Account viewAccount(Long id) {
        return accountRepository.findById(id);
    }
}
