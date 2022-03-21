package bussiness_layer.service.employee;

import bussiness_layer.models.Account;
import bussiness_layer.models.Action;
import bussiness_layer.models.User;

import java.util.List;

public interface EmployeeService {

  List<User> getAllCustomers ();

  List<Account>  getClientAccount (String username);

  boolean addCustomer (User user);

  boolean updateCustomer (String username, User newUser);

  User findByUsername (String username);

  boolean deleteAccount (Long id);

  boolean updateAccount (Long id, Account newAccount);

  boolean insertAccount (String username, Account newAccount);

  Account getById (Long id);

  void moveMoney (Long sourceId, Long destinationId, Float amount);

  void payBills (Long accountId, Float amount);

  void insertAction (Action action);

  void setUsername(String username);

}
