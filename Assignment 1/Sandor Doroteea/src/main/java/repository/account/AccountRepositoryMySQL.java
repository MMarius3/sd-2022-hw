package repository.account;

import model.Account;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.CLIENT;

public class AccountRepositoryMySQL implements AccountRepository{
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` ";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);

            while(accountResultSet.next()) {

                Account account = new Account();
                account.setId(accountResultSet.getLong("id"));
                account.setBalance(accountResultSet.getLong("balance"));
                account.setType(accountResultSet.getString("type"));
                account.setDateOfCreation(accountResultSet.getDate("dateOfCreation"));
                account.setClient_id(accountResultSet.getLong("client_id"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public List<Account> findAllClientAccounts(Long id) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `client_id`=\'" + id +  "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);

            while(accountResultSet.next()) {

                Account account = new Account();
                account.setId(accountResultSet.getLong("id"));
                account.setBalance(accountResultSet.getLong("balance"));
                account.setType(accountResultSet.getString("type"));
                account.setDateOfCreation(accountResultSet.getDate("dateOfCreation"));
                account.setClient_id(accountResultSet.getLong("client_id"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public Account findByClientID(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `client_id`=\'" + id +  "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Account account = new Account();
            account.setId(id);
            account.setBalance(clientResultSet.getLong("balance"));
            account.setType(clientResultSet.getString("type"));
            account.setDateOfCreation(clientResultSet.getDate("dateOfCreation"));
            account.setClient_id(clientResultSet.getLong("client_id"));

            return account;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO account values (default , ?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setLong(1, account.getBalance());
            insertAccountStatement.setString(2, account.getType());
            insertAccountStatement.setDate(3, (new java.sql.Date(account.getDateOfCreation().getTime())));
            insertAccountStatement.setLong(4, account.getClient_id());

            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            account.setId(clientId);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `id`=\'" + id +  "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Account account = new Account();
            account.setId(id);
            account.setBalance(clientResultSet.getLong("balance"));
            account.setType(clientResultSet.getString("type"));
            account.setDateOfCreation(clientResultSet.getDate("dateOfCreation"));
            account.setClient_id(clientResultSet.getLong("client_id"));

            return account;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean removeById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql =  "Delete from `" + ACCOUNT + "` where `id`=\'" + id +  "\'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement( "UPDATE account SET balance = ?, type = ?, dateOfCreation = ?, client_id = ? where `id`=\'" + account.getId() +  "\'", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setLong(1, account.getBalance());
            updateAccountStatement.setString(2, account.getType());
            updateAccountStatement.setDate(3, (new java.sql.Date(account.getDateOfCreation().getTime())));
            updateAccountStatement.setLong(4, account.getClient_id());

            updateAccountStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
