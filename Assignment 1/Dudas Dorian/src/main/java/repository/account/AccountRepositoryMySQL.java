package repository.account;

import model.Account;
import model.builder.AccountBuilder;

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
        try {
            PreparedStatement statement = connection
                    .prepareStatement("Select * from account where id = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                return Optional.of(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?)");
            insertStatement.setLong(1, account.getClientId());
            insertStatement.setString(2, account.getAccountType());
            insertStatement.setLong(3, account.getMoneyAmount());
            insertStatement.setDate(4, new Date(account.getCreationDate().getTime()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateById(Long id, Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE account SET account_type=?, money_amount=?, cnp=?, creation_date=? WHERE id=?");

            insertStatement.setString(1, account.getAccountType());
            insertStatement.setLong(2, account.getMoneyAmount());
            insertStatement.setDate(3, new Date(account.getCreationDate().getTime()));
            insertStatement.setLong(5, id);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeById(Long id) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE from account WHERE id=?");

            statement.setLong(1, id);
            statement.executeUpdate();
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
                .setClientId(rs.getLong("client_id"))
                .setAccountType(rs.getString("account_type"))
                .setMoneyAmount(rs.getLong("money_amount"))
                .setCreationDate(new Date(rs.getDate("creation_date").getTime()))
                .build();
    }


}
