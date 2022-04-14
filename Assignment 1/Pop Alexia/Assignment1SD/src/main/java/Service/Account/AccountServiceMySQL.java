package Service.Account;

import Model.Account;
import Model.Builder.AccountBuilder;
import Repository.Account.AccountRepository;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDate;

public class AccountServiceMySQL implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public boolean addAccount(Long cardnr,String type,int amount) {
        Account account = new AccountBuilder()
                .setAccNr(cardnr)
                .setType(type)
                .setAmount(amount)
                .setDate(Date.valueOf(LocalDate.now()))
                .build();
        return accountRepository.save(account);
    }

    @Override
    public boolean deleteAccount(Long accNr) {
        return accountRepository.delete(accNr);
    }

    @Override
    public ObservableList<Account> viewAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public boolean updateAccount(Long accnr,String type,int amount) {
        return accountRepository.update(accnr,type,amount);
    }

    @Override
    public boolean transferMoney(Long accnr1,Long accnr2,int amount){
        return accountRepository.transfer(accnr1,accnr2,amount);
    }

    @Override
    public boolean payBill(Long accNr,int amount){
        return accountRepository.payBill(accNr,amount);
    }

}
