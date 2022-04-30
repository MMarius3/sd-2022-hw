package service.account;

import model.Account;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService {

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
            .orElseThrow(() -> new IllegalArgumentException("No such id"));
  }

  @Override
  public boolean save(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public boolean update(Long id, Account account) {
    return accountRepository.update(id, account);
  }

  @Override
  public boolean deleteById(Long id) {
    return accountRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    accountRepository.deleteAll();
  }

  @Override
  public List<Account> findAccountsByClientId(Long clientId) {
    return accountRepository.findAccountsByClientId(clientId);
  }
}
