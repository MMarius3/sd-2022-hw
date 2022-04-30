package service.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import repository.account.AccountRepository;

public class AccountServiceMySQL implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean register(Long client_id, Long account_balance, String account_type) {

        Account account=new AccountBuilder()
                .setClient_id(client_id)
                .setType(account_type)
                .setBalance(account_balance)
                .build();


        return accountRepository.save(account);
    }

}