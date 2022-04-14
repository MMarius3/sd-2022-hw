package repository.user;

import model.Account;
import model.Client;
import model.User;
import helpers.validation.Notification;

import java.util.List;

public interface UserRepository {

  List<User> findAll();

  Notification<User> findByUsernameAndPassword(String username, String password);

  boolean save(User user);

  void removeAll();

//  boolean updateClient(Client client);

  List<Client> findAllClients();

  List<Account> findAccountsForClient(Long clientId);

  Notification<List<User>> findUsersWithoutAccount();

  Notification<Client> createClient(Client c);

  Notification<Boolean> updateClient(Client c);

  Notification<Boolean> deleteClientById(Long clientId);

  User findById(Long userId);

  Notification<Boolean> deleteUserById(Long id);

  Notification<Boolean> addRole(Long id, String role);

  Notification<Boolean> removeRole(Long id, String role);
}
