package service.user;

import model.Account;
import model.Action;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ActionBuilder;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import repository.account.AccountRepositoryMySQL;
import repository.action.ActionRepository;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.user.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

import static database.Constants.Tables.*;

public class ActionServiceMySQL implements ActionService{


    private final Connection connection;
    private final ActionRepository actionRepository;

    public ActionServiceMySQL(Connection connection, ActionRepository actionRepository){
        this.connection = connection;
        this.actionRepository = actionRepository;
    }
    @Override
    public Client findByName(String name) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `name`=\'" + name + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();

            Client client = new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setName(clientResultSet.getString("name"))
                    .setCardId(clientResultSet.getString("cardId"))
                    .setPersonalNumericalCode(clientResultSet.getString("personalNumericalCode"))
                    .setAddress(clientResultSet.getString("address"))
                    .build();

            return client;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    @Override
    public void createAction(User user,String type,String description) throws SQLException {
        Action action = new ActionBuilder()
                .setUser(user)
                .setType(type)
                .setDescription(description)
                .build();
        actionRepository.save(action);
    }


    @Override
    public Boolean createClient(String name, String cardId, String personalNumericalCode, String address) {
        Client client = new ClientBuilder()
                .setName(name)
                .setCardId(cardId)
                .setPersonalNumericalCode(personalNumericalCode)
                .setAddress(address)
                .build();
        return new ClientRepositoryMySQL(connection).save(client);

    }

    @Override
    public Boolean  updateClient(String category, Client client, String name, String cardId, String personalNumericalCode, String address) {
        switch(category){
            case "name" -> {
                try {
                    PreparedStatement updateClientStatement = connection
                            .prepareStatement("UPDATE `" + CLIENT +"` set `name` = \'"+name+"\' where id = \'"+client.getId() +"\'");
                    updateClientStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            case "cardId" ->{
                try {
                    PreparedStatement updateClientStatement = connection
                            .prepareStatement("UPDATE `" + CLIENT +"` set `cardId` = \'"+cardId+"\' where id = \'"+client.getId() +"\'");
                    updateClientStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            case "address" ->{
                try {
                    PreparedStatement updateClientStatement = connection
                            .prepareStatement("UPDATE `" + CLIENT +"` set `address` = \'"+address+"\' where id = \'"+client.getId() +"\'");
                    updateClientStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            case "personalNumericalCode" ->{
                try {
                    PreparedStatement updateClientStatement = connection
                            .prepareStatement("UPDATE `" + CLIENT +"` set `personalNumericalCode` = \'"+personalNumericalCode+"\' where id = \'"+client.getId() +"\'");
                    updateClientStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public void deleteClient(Client client) {
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("DELETE from `" + CLIENT +"` where id = \'"+client.getId() +"\'");
            updateClientStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Boolean createAccount(String clientId, String type, Long balance, Date createdAt) throws SQLException {
        Account account = new AccountBuilder()
                .setClientId(clientId)
                .setType(type)
                .setBalance(balance)
                .setDate(createdAt)
                .build();
        return new AccountRepositoryMySQL(connection).save(account);
    }

    @Override
    public Boolean updateAccount(String category,Account account, String clientId, String type, Long balance) {
        switch(category){
            case "clientId" -> {
                try {
                    PreparedStatement updateAccountStatement = connection
                            .prepareStatement("UPDATE `" + ACCOUNT +"` set `clientId` = \'"+clientId+"\' where id = \'"+ account.getId() +"\'");
                    updateAccountStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            case "type" ->{
                try {
                    PreparedStatement updateAccountStatement = connection
                            .prepareStatement("UPDATE `" + ACCOUNT +"` set `type` = \'"+type+"\' where id = \'"+account.getId() +"\'");
                    updateAccountStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            case "balance" ->{
                try {
                    PreparedStatement updateAccountStatement = connection
                            .prepareStatement("UPDATE `" + ACCOUNT +"` set `balance` = \'"+balance+"\' where id = \'"+account.getId() +"\'");
                    updateAccountStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteAccount(Account account) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("DELETE from `" + ACCOUNT +"` where id = \'"+account.getId() +"\'");
            updateAccountStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void transaction(Account accountFrom, Long sum, Account accountTo) throws SQLException {
        if(accountFrom.getBalance()<sum){
            return;
        }

        long balance1 = accountTo.getBalance() + sum;

        long balance2 = accountFrom.getBalance() - sum;
        PreparedStatement updateAccountToStatement = connection
                .prepareStatement("UPDATE `" + ACCOUNT +"` set `balance` = \'"+balance1+"\' where id = \'"+ accountTo.getId() +"\'");
        updateAccountToStatement.executeUpdate();

        PreparedStatement updateAccountFromStatement = connection
                .prepareStatement("UPDATE `" + ACCOUNT +"` set `balance` = \'"+balance2+"\' where id = \'"+ accountFrom.getId() +"\'");
        updateAccountFromStatement.executeUpdate();

    }
}
