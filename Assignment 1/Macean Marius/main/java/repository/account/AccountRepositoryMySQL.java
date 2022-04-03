package repository.account;

import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryMySQL implements AccountRepository {

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
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("Select * from account where id = ?");
            insertStatement.setString(1, id.toString());
            ResultSet rs = insertStatement.executeQuery();
            rs.next();
            return Optional.of(getAccountFromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setInt(2, account.getMoneyAmount());
            insertStatement.setDate(3, new Date(account.getCreationDate().getTime()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long lastId() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM account WHERE" +
                    " id = (SELECT MAX(id) FROM account)";
            ResultSet rs = statement.executeQuery(sql);

            rs.next();
            return rs.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE account SET type = ?, moneyAmount = ?, creationDate = ? WHERE id = ?");
            insertStatement.setString(1, account.getType());
            insertStatement.setInt(2, account.getMoneyAmount());
            insertStatement.setDate(3, new Date(account.getCreationDate().getTime()));
            insertStatement.setLong(4, account.getId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("DELETE from account where id = ?");
            insertStatement.setString(1, id.toString());
            insertStatement.executeUpdate();
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
                .setId(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setMoneyAmount(rs.getInt("moneyAmount"))
                .setCreationDate(new Date(rs.getDate("creationDate").getTime()))
                .build();
    }

}