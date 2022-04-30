package repository.account;

import controller.Response;
import model.Account;
import model.User;
import model.builder.AccountBuilder;
import model.builder.UserBuilder;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;


    public AccountRepositoryMySQL(Connection connection){
        this.connection = connection;

    }

    @Override
    public List<Account> findAll() {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from " + ACCOUNT;
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            List<Account> accounts = new ArrayList<>();
            while(accountResultSet.next()) {
                Account account = new AccountBuilder()
                        .setId(accountResultSet.getLong("id"))
                        .setClientId(accountResultSet.getString("clientId"))
                        .setType(accountResultSet.getString("type"))
                        .setBalance(accountResultSet.getLong("balance"))
                        .setDate(new java.util.Date(accountResultSet.getDate("created_at").getTime()))
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
    public Account findByClient(String clientId) {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `clientId`=\'" + clientId + "\'" ;
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            Account account = new AccountBuilder()
                    .setClientId(accountResultSet.getString("clientId"))
                    .setType(accountResultSet.getString("type"))
                    .setBalance(accountResultSet.getLong("balance"))
                    .build();
            account.setId(accountResultSet.getLong("id"));

            return account;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Account account) throws SQLException {
        try{
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO `"+ACCOUNT+"` values (null, ?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setString(1, account.getClientId());
            insertAccountStatement.setString(2, account.getType());
            insertAccountStatement.setLong(3,account.getBalance());
            insertAccountStatement.setDate(4, new java.sql.Date(account.getDate().getTime()));
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong(1);
            account.setId(accountId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + ACCOUNT + "` where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByClientId(Long clientId) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `clientId`=\'" + clientId + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(accountResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
}
