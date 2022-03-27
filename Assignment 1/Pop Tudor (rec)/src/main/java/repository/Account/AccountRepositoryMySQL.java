package repository.Account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        String sql = "Select * from account";

        List<Account> accounts = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                accounts.add(getAccountFromResultSet(resultSet));
            }

        } catch (SQLException trowables) {
            trowables.printStackTrace();
        }
        return accounts;
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setIdentificationNR(rs.getInt("identificationNr"))
                .setType(rs.getString("type"))
                .setMoneyAmount(rs.getInt("moneyAmount"))
                .setCreationDate(LocalDate.ofEpochDay(rs.getDate("creationDate").getTime()))
                .build();
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        String sql = "Select * from account where id = " + id;

        Account account = new Account();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                account = getAccountFromResultSet(resultSet);
            }
        } catch (SQLException trowables) {
            trowables.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }

        return account;
    }

    @Override
    public Account findByClientId(Long clientId) throws  EntityNotFoundException{
        String sql = "Select * from client_account where client_id=" + clientId;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                Account account = new AccountBuilder()
                        .setId(resultSet.getLong("account_id"))
                        .build();

                return findById(account.getId());
            } else {
                throw new EntityNotFoundException(clientId, Account.class.getSimpleName());
            }
        } catch (SQLException | EntityNotFoundException trowables) {
            trowables.printStackTrace();
            throw new EntityNotFoundException(clientId, Account.class.getSimpleName());
        }
    }

    @Override
    public boolean save(Account account) {
        String sql = "INSERT INTO account values (null, ?, ?, ?, ?)";
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setInt(1, account.getIdentificationNr());
            insertStatement.setString(2, account.getType());
            insertStatement.setInt(3, account.getMoneyAmount());
            insertStatement.setDate(4, new java.sql.Date(account.getCreationDate().toEpochDay()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE from account where id >= 0";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException trowables) {
            trowables.printStackTrace();
        }
    }

    @Override
    public boolean remove(Long id) {
        String sql = "DELETE from account where id = " + id;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException trowables) {
            trowables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE account SET IdentificationNr = ?, type = ?, moneyAmount = ?, creationDate = ? WHERE id = " + account.getId();

        try {
            PreparedStatement updateStatement = connection.prepareStatement(sql);
            updateStatement.setLong(1, account.getIdentificationNr());
            updateStatement.setString(2, account.getType());
            updateStatement.setInt(3, account.getMoneyAmount());
            updateStatement.setDate(4, new java.sql.Date(account.getCreationDate().toEpochDay()));
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException trowables) {
            trowables.printStackTrace();
            return false;
        }
    }

    @Override
    public void transfer(Account account1, Account account2, Integer amount) {
        account1.setMoneyAmount(account1.getMoneyAmount() - amount);
        account2.setMoneyAmount(account2.getMoneyAmount() + amount);

        update(account1);
        update(account2);
    }
}

