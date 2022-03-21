package repository.account;

import model.Account;
import model.builder.AccountBuilder;

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
    public Account findByID(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            return new AccountBuilder()
                    .setID(id)
                    .setType(accountResultSet.getString("type"))
                    .setAmount(accountResultSet.getFloat("amount"))
                    .setDateOfCreation(new Date(accountResultSet.getDate("date_of_creation").getTime()))
                    .build();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public long save(Account account) {
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setString(1, account.getType());
            insertAccountStatement.setFloat(2, account.getAmount());
            insertAccountStatement.setDate(3, (java.sql.Date) account.getDateOfCreation());
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong(1);
            account.setId(accountId);

            return accountId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public boolean updateType(Account account, String type) {
        Float amount = account.getAmount();
        Date date = account.getDateOfCreation();
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE account SET type = ? WHERE amount = ? AND date_of_creation = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1, type);
            updateAccountStatement.setFloat(2, amount);
            updateAccountStatement.setDate(3, date);
            updateAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAmount(Account account, Float amount) {
        String type = account.getType();
        Date date = account.getDateOfCreation();
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE account SET amount = ? WHERE type = ? AND date_of_creation = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(2, type);
            updateAccountStatement.setFloat(1, amount);
            updateAccountStatement.setDate(3, date);
            updateAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateDate(Account account, Date date) {
        String type = account.getType();
        Float amount = account.getAmount();
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE account SET date_of_creation = ? WHERE type = ? AND amount = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(2, type);
            updateAccountStatement.setFloat(3, amount);
            updateAccountStatement.setDate(1, date);
            updateAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Account account) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE account SET type = ?, amount = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1, account.getType());
            updateAccountStatement.setFloat(2, account.getAmount());
            updateAccountStatement.setLong(3, id);
            updateAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByID(Long id) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("DELETE from account WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setLong(1, id);
            updateAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account account) {
        String type = account.getType();
        Float amount = account.getAmount();
        Date date = account.getDateOfCreation();
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("DELETE from account WHERE type = ? AND amount = ? AND date_of_creation = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1, type);
            updateAccountStatement.setFloat(2, amount);
            updateAccountStatement.setDate(3, date);
            updateAccountStatement.executeUpdate();
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

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setID(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setAmount(rs.getFloat("amount"))
                .setDateOfCreation(new Date(rs.getDate("date_of_creation").getTime()))
                .build();
    }
}
