package repository.client;

import model.Account;
import model.Client;
import model.Role;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class ClientAccountRepositoryMySQL implements ClientAccountRepository{
    private final Connection connection;

    public ClientAccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addAccountsToClient(Client client, List<Account> accounts) {
        try {
            for (Account account : accounts) {
                PreparedStatement insertClientAccountStatement = connection
                        .prepareStatement("INSERT INTO client_account values (null, ?, ?)");
                insertClientAccountStatement.setLong(1, client.getId());
                insertClientAccountStatement.setLong(2, account.getId());
                insertClientAccountStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public Account findAccountById(Long accountId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + ACCOUNT + " where `id`=\'" + accountId + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();
            String accountType = accountResultSet.getString("type");
            Float accountAmount = accountResultSet.getFloat("amount");
            Date accountDateOC = accountResultSet.getDate("date_of_creation");
            return new AccountBuilder()
                    .setID(accountId)
                    .setType(accountType)
                    .setAmount(accountAmount)
                    .setDateOfCreation(accountDateOC)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Account> findAccountsForClient(Long clientId) {
        try {
            List<Account> accounts = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + CLIENT_ACCOUNT + " where `client_id`=\'" + clientId + "\'";
            ResultSet clientAccountResultSet = statement.executeQuery(fetchAccountSql);
            while (clientAccountResultSet.next()) {
                long accountId = clientAccountResultSet.getLong("account_id");
                accounts.add(findAccountById(accountId));
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public void addClientAccount(Long clientId, Long accountId) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + CLIENT_ACCOUNT + " values (null, ?, ?)");
            insertStatement.setLong(1, clientId);
            insertStatement.setLong(2, accountId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client_account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
