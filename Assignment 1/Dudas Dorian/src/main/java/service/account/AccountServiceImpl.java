package service.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account with id %d not found".formatted(id)));
    }

    @Override
    public boolean save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean updateById(Long id, Account account) {
        return updateById(id, account);
    }

    @Override
    public boolean removeById(Long id) {
        return removeById(id);
    }

    @Override
    public boolean transferMoney(Long fromId, Long toId, Long transferAmount) {
        Account transferAccount = accountRepository.findById(fromId).orElseGet(() -> new AccountBuilder().setMoneyAmount(-1L).build());
        if(transferAccount.getMoneyAmount() < transferAmount){
            return false;
        }
        Account receivingAccount = accountRepository.findById(toId).orElseGet(() -> new AccountBuilder()
                .setAccountType("invalid").build());
        if(receivingAccount.getAccountType().equals("invalid")){
            return false;
        }
        transferAccount.setMoneyAmount(transferAccount.getMoneyAmount() - transferAmount);
        receivingAccount.setMoneyAmount(receivingAccount.getMoneyAmount() + transferAmount);
        return updateById(fromId, transferAccount) && updateById(toId, receivingAccount);
    }

    @Override
    public boolean processBillsForId(Long id, Long billValue) {
        Account billedAccount = accountRepository.findById(id).orElseGet(() -> new AccountBuilder().setMoneyAmount(-1L).build());
        if(billedAccount.getMoneyAmount() < billValue){
            return false;
        }
        billedAccount.setMoneyAmount(billedAccount.getMoneyAmount() - billValue);
        return updateById(id, billedAccount);
    }
}
