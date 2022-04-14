package repository.account;

import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryMySQL implements AccountRepository {
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() throws SQLException {
        String sql = "SELECT * FROM account";

        List<Account> accounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Optional<Account> findById(Long id) {
        String sql = "SELECT * FROM account WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Account account = getAccountFromResultSet(resultSet);
                return Optional.of(account);
            } else {
                System.out.println("Account with id: " + id + " not found");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean save(Account account) {
        String sql = "INSERT INTO account values (null, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setString(1, account.getType());
            insertStatement.setFloat(2, account.getBalance());
            insertStatement.setDate(3, new java.sql.Date(account.getDateOfCreation().toEpochDay()));
            insertStatement.setLong(4, account.getClientID());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, account.getBalance());
            preparedStatement.setLong(2, account.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE from account where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setBalance(rs.getFloat("balance"))
                .setDateOfCreation(rs.getDate("dateOfCreation").toLocalDate())
                .setClientID(rs.getLong("clientID"))
                .build();
    }
}
