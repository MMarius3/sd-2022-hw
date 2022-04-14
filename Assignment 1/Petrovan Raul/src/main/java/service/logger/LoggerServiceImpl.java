package service.logger;

import helpers.validation.Notification;
import lombok.RequiredArgsConstructor;
import model.Account;
import model.ActionLog;
import model.Client;
import model.Role;
import model.User;
import repository.logger.LoggerRepository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class LoggerServiceImpl implements LoggerService{

    private final LoggerRepository loggerRepository;

    @Override
    public Notification<ActionLog> addUserLog(String username) {
        return loggerRepository.addLog(null, "User " + username + " has been added");
    }

    @Override
    public Notification<ActionLog> upgradeUserLog(Long userId, Role role, String to) {
        return loggerRepository.addLog(userId, "User has added role " + role.getRole() + " to " +
                "user " + to + ".");
    }

    @Override
    public Notification<ActionLog> downgradeUserLog(Long userId, Role role, String to) {
        return loggerRepository.addLog(userId, "User has removed role " + role.getRole() + " to " +
                "user " + to + ".");
    }

    @Override
    public Notification<ActionLog> transferMoney(Long userId, String sum, String from, String to) {
        return loggerRepository.addLog(userId, "User has transferred amount " + sum + " from " +
                "account " + from + " to account " + to);
    }

    @Override
    public Notification<ActionLog> payBill(Long userId, String sum, String description,
                                           Client selectedClient) {
        return loggerRepository.addLog(userId, "User has paid the bill in the amount of " + sum +
                " with the following description: " + description + " for the client " + selectedClient.getName() + ".");
    }

    @Override
    public Notification<ActionLog> editClient(Long userId, Client client) {
        return loggerRepository.addLog(userId,
                "User has edited the client with id" + client.getId() + " and name " + client.getName());
    }

    @Override
    public Notification<ActionLog> addClient(Long userId, Client client) {
        return loggerRepository.addLog(userId,
                "User has added the client with id" + client.getId() + " and name " + client.getName());
    }

    @Override
    public Notification<ActionLog> removeClient(Long userId, Client client) {
        return loggerRepository.addLog(userId,
                "User has removed the client with id" + client.getId() + " and name " + client.getName());
    }

    @Override
    public Notification<ActionLog> addAccount(Long id, Account account) {
        return loggerRepository.addLog(id,
                "User has added the account with id" + account.getId() + " and number " + account.getAccountNumber());
    }

    @Override
    public Notification<ActionLog> deleteAccount(Long id, Account account) {
        return loggerRepository.addLog(id,
                "User has deleted the account with id" + account.getId() + " and number " + account.getAccountNumber());
    }

    @Override
    public Notification<ActionLog> editAccount(Long id, Account account) {
        return loggerRepository.addLog(id,
                "User has edited the account with id" + account.getId() + " and number " + account.getAccountNumber());
    }

    @Override
    public Notification<ActionLog> removeUser(Long id, User selectedUser) {
        return loggerRepository.addLog(id,
                "User has removed the user with id" + selectedUser.getId() + " and username " + selectedUser.getUsername());
    }

    @Override
    public Notification<ActionLog> addRole(Long id, User selectedUser, String role) {
        return loggerRepository.addLog(id,
                "User has added the role " + role + " to user " + selectedUser.getUsername());
    }

    @Override
    public Notification<ActionLog> removeRole(Long id, User selectedUser, String role) {
        return loggerRepository.addLog(id,
                "User has removed the role " + role + " from user " + selectedUser.getUsername());
    }

    @Override
    public Notification<List<ActionLog>> getReport(Date from, Date to, Long forUserId) {
        return loggerRepository.fetchLogs(from, to, forUserId);
    }
}
