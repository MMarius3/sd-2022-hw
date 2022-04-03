package service.account;

import model.Account;
import model.Client;
import repository.account.AccountRepository;
import repository.client_account.ClientAccountRepository;

import java.time.LocalDate;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ClientAccountRepository clientAccountRepository;

    public AccountServiceImpl(AccountRepository repository, ClientAccountRepository clientAccountRepository) {
        this.repository = repository;
        this.clientAccountRepository = clientAccountRepository;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
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
    public boolean saveAccount(Account account, Long clientId) {
        repository.save(account);
        Long accountId = repository.lastId();
        return clientAccountRepository.addClientAccount(clientId, accountId);
    }

    public boolean update(Account account) {
        return repository.update(account);
    }

    public boolean delete(Long id) {
        return repository.delete(id);
    }

    public boolean transfer(Long id1, Long id2, Integer moneyAmountToTransfer) {
        Account account1 = findById(id1);
        Account account2 = findById(id2);
        account1.setMoneyAmount(account1.getMoneyAmount() - moneyAmountToTransfer);
        account2.setMoneyAmount(account2.getMoneyAmount() + moneyAmountToTransfer);
        return repository.update(account1) && repository.update(account2);
    }

    public boolean bill(Long id, Integer billToExecute) {
        Account account = findById(id);
        account.setMoneyAmount(account.getMoneyAmount() - billToExecute);
        return repository.update(account);
    }

    @Override
    public boolean addAccount(String type, Integer moneyAmount, Long clientId) {
        Account account = new Account();
        account.setType(type);
        account.setMoneyAmount(moneyAmount);
        account.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
        return saveAccount(account, clientId);
    }

    @Override
    public boolean updateAccount(Long id, String type, Integer moneyAmount) {
        Account account = repository.findById(id).orElse(new Account());
        account.setType(type);
        account.setMoneyAmount(moneyAmount);
        account.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
        return update(account);
    }

    @Override
    public boolean deleteAccount(Long id) {
        return delete(id);
    }

    @Override
    public Account viewAccount(Long id) {
        return findById(id);
    }

    @Override
    public boolean transferMoney(Long id1, Long id2, Integer moneyAmountToTransfer) {
        return transfer(id1, id2, moneyAmountToTransfer);
    }

    @Override
    public boolean executeBill(Long id, Integer billToExecute) {
        return bill(id, billToExecute);
    }
}