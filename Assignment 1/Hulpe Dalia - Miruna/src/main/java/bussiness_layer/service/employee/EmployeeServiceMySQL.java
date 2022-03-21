package bussiness_layer.service.employee;

import bussiness_layer.models.Account;
import bussiness_layer.models.Action;
import bussiness_layer.models.User;
import repository_layer.repository.account.AccountRepository;
import repository_layer.repository.action.ActionRepository;
import repository_layer.repository.user.UserRepository;
import repository_layer.repository.user_role.UserRoleRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class EmployeeServiceMySQL implements EmployeeService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final AccountRepository accountRepository;
  private final ActionRepository actionRepository;
  private String CurrentUsername;

  public EmployeeServiceMySQL(UserRepository userRepository, UserRoleRepository userRoleRepository, AccountRepository accountRepository, ActionRepository actionRepository) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.accountRepository = accountRepository;
    this.actionRepository = actionRepository;
  }

  private static String encodePassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public void setUsername(String username) {
    this.CurrentUsername = username;
  }

  @Override
  public List<User> getAllCustomers() {
    return userRoleRepository.getUsersByRole("customer");
  }

  @Override
  public List<Account> getClientAccount(String username) {
    return accountRepository.getUserAccounts(username);
  }

  @Override
  public boolean addCustomer(User user) {
    user.setPassword(encodePassword(user.getPassword()));
    return userRoleRepository.insert("customer", user);
  }

  @Override
  public boolean updateCustomer(String username, User newUser) {
    return userRepository.update(username, newUser);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public boolean deleteAccount(Long id) {
    return accountRepository.remove(id);
  }

  @Override
  public boolean updateAccount(Long id, Account newAccount) {
    return accountRepository.update(id, newAccount);
  }

  @Override
  public boolean insertAccount(String username, Account newAccount) {
    return accountRepository.insert(newAccount,username);
  }

  @Override
  public Account getById(Long id) {
    return accountRepository.getById(id);
  }

  @Override
  public void moveMoney(Long sourceId, Long destinationId, Float amount) {
    Account sourceAccount = accountRepository.getById(sourceId);
    Account destinationAccount = accountRepository.getById(destinationId);
    sourceAccount.setAmount_of_money(sourceAccount.getAmount_of_money() - amount);
    destinationAccount.setAmount_of_money(destinationAccount.getAmount_of_money() + amount);

    boolean test1 = accountRepository.update(sourceId,sourceAccount);
    boolean test2 = accountRepository.update(destinationId,destinationAccount);
  }

  @Override
  public void payBills(Long accountId, Float amount) {
    Account account = accountRepository.getById(accountId);
    account.setAmount_of_money(account.getAmount_of_money() - amount);

    boolean test = accountRepository.update(accountId,account);
  }

  @Override
  public void insertAction(Action action) {
    actionRepository.insert(action,CurrentUsername);
  }
}
