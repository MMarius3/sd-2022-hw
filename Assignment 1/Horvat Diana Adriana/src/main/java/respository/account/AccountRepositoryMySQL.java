package respository.account;

import controller.Response;
import model.Account;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;
    public static final String ACCOUNT = "accounts";

    public AccountRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Account> findAll(){
        try {
            Statement statement = connection.createStatement();

            String getAllAccountsSql =
                    "Select * from `" + ACCOUNT + "`";
            ResultSet accountResultSet = statement.executeQuery(getAllAccountsSql);

            List<Account> accounts = new ArrayList<>();

            while(accountResultSet.next()){
                Account account = new AccountBuilder()
                        .setId(accountResultSet.getInt("id"))
                        .setClientId(accountResultSet.getInt("client_id"))
                        .setType(accountResultSet.getString("type"))
                        .setAmount(accountResultSet.getLong("amount"))
                        .setDateOfCreation(accountResultSet.getTimestamp("created_at"))
                        .build();

                accounts.add(account);
            }

            return accounts;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public List<Account> findByClientId(int id){
        try {
            Statement statement = connection.createStatement();

            String fetchAccountsSql =
                    "Select * from `" + ACCOUNT + "` where `client_id`=\'" + id + "\'";
            ResultSet accountsResultSet = statement.executeQuery(fetchAccountsSql);

            List<Account> accounts = new ArrayList<>();

            while(accountsResultSet.next()){
                Account account = new AccountBuilder()
                        .setId(accountsResultSet.getInt("id"))
                        .setClientId(accountsResultSet.getInt("client_id"))
                        .setType(accountsResultSet.getString("type"))
                        .setAmount(accountsResultSet.getLong("amount"))
                        .setDateOfCreation(accountsResultSet.getTimestamp("created_at"))
                        .build();

                accounts.add(account);
            }

            return accounts;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Account account){
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO accounts values (null, ?, ?, ?, null)", Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setInt(1, account.getClient_id());
            insertAccountStatement.setString(2, account.getType());
            insertAccountStatement.setLong(3, account.getAmount());
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeAccount(Account account){
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + ACCOUNT + "` where `id`=\'" + account.getId() + "\'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Account account){
        try {
            String fetchAccountSql =
                    "UPDATE accounts SET client_id = ?, type = ?,amount=? WHERE id= ?";
            PreparedStatement statement = connection.prepareStatement(fetchAccountSql);
            statement.setInt(1, account.getClient_id());
            statement.setString(2, account.getType());
            statement.setLong(3, account.getAmount());
            statement.setInt(4, account.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Account findById(int id){
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            Account account = new AccountBuilder()
                    .setId(accountResultSet.getInt("id"))
                    .setClientId(accountResultSet.getInt("client_id"))
                    .setType(accountResultSet.getString("type"))
                    .setAmount(accountResultSet.getLong("amount"))
                    .setDateOfCreation(accountResultSet.getTimestamp("created_at"))
                    .build();

            return account;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
