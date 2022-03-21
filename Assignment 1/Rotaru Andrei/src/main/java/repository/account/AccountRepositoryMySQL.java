package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository{
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts  = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql = "SELECT * FROM `account`";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);

            while (accountResultSet.next()) {
                accounts.add(getAccountFromResultSet(accountResultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Notification<Account> findById(Long id) {
        Notification<Account> findByIdNotification = new Notification<>();
        try{
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from `" + ACCOUNT +"` WHERE `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);

            if (accountResultSet.next()) {
                findByIdNotification.setResult(getAccountFromResultSet(accountResultSet));
                return findByIdNotification;
            } else {
                findByIdNotification.addError("Invalid id account");
                return findByIdNotification;
            }
        }catch(SQLException e){
            e.printStackTrace();
            findByIdNotification.addError("Something is wrong with the Database");
        }
        return  findByIdNotification;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account VALUES (null, ?, ?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setDouble(2, account.getBalance());
            insertStatement.setDate(3, (Date) account.getDate());
            insertStatement.setLong(4, account.getIdClient());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement updateAccountStatement = connection.
                    prepareStatement("UPDATE account SET `type`=?, `balance`=?" +
                            " WHERE `id` = ?");
            updateAccountStatement.setString(1, account.getType());
            updateAccountStatement.setDouble(2, account.getBalance());
            updateAccountStatement.setLong(3, account.getId());
            updateAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account account) {
        try{
            PreparedStatement deleteAccountStatement = connection.prepareStatement("DELETE from account where id = ?");
            deleteAccountStatement.setLong(1, account.getId());
            deleteAccountStatement.executeUpdate();
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
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> findAccountsByClientId(Long id) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from account where id_client =\'" + id +"\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);

            while (accountResultSet.next()) {
                accounts.add(getAccountFromResultSet(accountResultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setBalance(rs.getDouble("balance"))
                .setDate(rs.getDate("date"))
                .setIdClient(rs.getLong("id_client"))
                .build();
    }
}
