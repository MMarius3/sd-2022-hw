package service.account;

import model.Account;
import model.Client;
import repository.account.AccountRepository;
import repository.client.ClientAccountRepository;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CRUDAccountMySQL implements CRUDAccount{

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ClientAccountRepository clientAccountRepository;

    public CRUDAccountMySQL(ClientRepository clientRepository, AccountRepository accountRepository, ClientAccountRepository clientAccountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.clientAccountRepository = clientAccountRepository;
    }

    @Override
    public List<Account> viewAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> viewAccountForClient(Long clientId) {
        return clientAccountRepository.findAccountsForClient(clientId);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findByID(id);
    }

    @Override
    public boolean update(Long id, Account account) {
        return accountRepository.update(id, account);
    }

    @Override
    public boolean addAccount(Account account) {
        return accountRepository.save(account) != -1L;
    }

    @Override
    public boolean updateAccount(Account account, Account updatedAccount, AccountUpdateFields fields) {
        boolean update = false;
        switch (fields){
            case TYPE -> update = accountRepository.updateType(account, updatedAccount.getType());
            case AMOUNT -> update = accountRepository.updateAmount(account, updatedAccount.getAmount());
            case DATE_OF_CREATION -> update = accountRepository.updateDate(account, updatedAccount.getDateOfCreation());
        }
        return update;
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accountRepository.delete(account);
    }

    @Override
    public boolean delete(Long id) {
        return accountRepository.deleteByID(id);
    }

    @Override
    public boolean openAccount(Client client, Account account) {
        Client foundClient = clientRepository.findByID(client.getId());
        if (foundClient != null) {
            if (Objects.equals(foundClient.getId(), client.getId())) {
                List<Account> accounts = new ArrayList<>();
                accountRepository.save(account);
                accounts.add(account);
                clientAccountRepository.addAccountsToClient(client, accounts);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean transferMoney(Account from, Account to, Float amount) {
        Account foundFromAccount = accountRepository.findByID(from.getId());
        Account foundToAccount = accountRepository.findByID(to.getId());
        if (Objects.equals(foundFromAccount.getId(), from.getId()) && Objects.equals(foundToAccount.getId(), to.getId())) {
            if (from.getAmount() - amount > 0.0) {
                from.setAmount(from.getAmount() - amount);
                updateAccount(from, from, AccountUpdateFields.AMOUNT);
                to.setAmount(to.getAmount() + amount);
                updateAccount(to, to, AccountUpdateFields.AMOUNT);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean extractMoney(Account from, Float amount) {
        if (from.getAmount() - amount > 0.0) {
            from.setAmount(from.getAmount() - amount);
            return updateAccount(from, from, AccountUpdateFields.AMOUNT);
        }
        else return false;
    }
}
