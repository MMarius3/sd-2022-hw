package service.logger;

import helpers.validation.Notification;
import model.Account;
import model.ActionLog;
import model.Client;
import model.Role;
import model.User;

import java.util.Date;
import java.util.List;

public interface LoggerService {
    Notification<ActionLog> addUserLog(String username);

    Notification<ActionLog> upgradeUserLog(Long userId, Role role, String to);

    Notification<ActionLog> downgradeUserLog(Long userId, Role role, String to);

    Notification<ActionLog> transferMoney(Long userId, String sum, String from, String to);

    Notification<ActionLog> payBill(Long userId, String sum, String description,
                                    Client selectedClient);

    Notification<ActionLog> editClient(Long userId, Client client);
    Notification<ActionLog> addClient(Long userId, Client client);
    Notification<ActionLog> removeClient(Long userId, Client client);


    Notification<ActionLog> addAccount(Long id, Account account);
    Notification<ActionLog> deleteAccount(Long id, Account selectedAccount);
    Notification<ActionLog> editAccount(Long id, Account selectedAccount);

    Notification<ActionLog> removeUser(Long id, User selectedUser);
    Notification<ActionLog> addRole(Long id, User selectedUser, String toString);
    Notification<ActionLog> removeRole(Long id, User selectedUser, String toString);

    Notification<List<ActionLog>> getReport(Date from, Date to, Long forUserId);
}
