package service.user;

import model.Account;
import model.Action;
import model.Client;
import model.User;
import model.builder.ActionBuilder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public interface ActionService {
    Boolean createClient(String name, String cardId, String personalNumericalCode, String Address);
    Boolean updateClient(String category, Client client, String name, String cardId, String personalNumericalCode, String Address);
    void deleteClient(Client client);
    void createAction(User user, String type, String description) throws SQLException;
    Boolean createAccount(String clientId, String type, Long balance, Date createdAt) throws SQLException;
    Boolean updateAccount(String category,Account account, String clientId, String type, Long balance);
    void deleteAccount(Account account);
    void transaction(Account accountFrom,Long sum, Account accountTo) throws SQLException;
    Client findByName(String name);
}
