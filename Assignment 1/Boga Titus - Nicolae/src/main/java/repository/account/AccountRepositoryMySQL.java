package repository.account;

import model.Account;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<Account> account = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id =" + id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                account= Optional.ofNullable(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return account;
    }

    @Override
    public boolean save(Account account) {
        try {
            java.sql.Date sqlDate= new java.sql.Date(account.getDate().getTime());
            System.out.println("INSERTING!!!!!!!!!!!!!!!");
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?,?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setInt(2, account.getAmount());
            insertStatement.setDate(3, sqlDate);
            insertStatement.setInt(4, Math.toIntExact(account.getClientId()));
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
            java.sql.Date sqlDate= new java.sql.Date(account.getDate().getTime());
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE account SET type=?, amount=?, date=?, client_id=? where id =?");
            insertStatement.setString(1, account.getType());
            insertStatement.setInt(2, account.getAmount());
            insertStatement.setDate(3, sqlDate);
            insertStatement.setInt(4, Math.toIntExact(account.getClientId()));
            insertStatement.setInt(5, Math.toIntExact(account.getId()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("DELETE FROM account where id=?");
            insertStatement.setInt(1, Math.toIntExact(id));

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> findByClientId(Long id) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where client_id =" + id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return accounts;
    }

    ///Running out of time to create a builder
    private Account getAccountFromResultSet(ResultSet rs) throws  SQLException{
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setType(rs.getString("type"));
        account.setAmount(rs.getInt("amount"));
        account.setDate(rs.getDate("date"));
        account.setClientId(rs.getLong("client_id"));

        return account;
    }
}
