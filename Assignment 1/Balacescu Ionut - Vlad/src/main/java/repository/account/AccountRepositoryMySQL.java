package repository.account;

import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.CLIENT_ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository {
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        try {
            List<Account> accounts = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT;
            ResultSet accountResult = statement.executeQuery(fetchRoleSql);
            while (accountResult.next()) {
                Account newAccount = new AccountBuilder().setId(accountResult.getLong("id"))
                        .setIdNumber(accountResult.getLong("id_Number"))
                        .setTypeAccount(accountResult.getString("type_account"))
                        .setAmountOfMoney(accountResult.getInt("amount_money"))
                        .setCreationDate(accountResult.getString("date_of_creation"))
                        .build();
                accounts.add(newAccount);
            }
            return accounts;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public boolean addAccount(Account account, long clientId) {

        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO " + ACCOUNT + " values (null, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setString(1, String.valueOf(account.getIdNumber()));
            insertAccountStatement.setString(2, account.getTypeAccount());
            insertAccountStatement.setString(3, String.valueOf(account.getAmountOfMoney()));
            insertAccountStatement.setString(4, account.getCreationDate());
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();
            long accoutId = rs.getLong(1);
            addAccountToClient(clientId, accoutId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addAccountToClient(long idClient, long idAccount) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO " + CLIENT_ACCOUNT + " values (null, ?, ?)");
            insertUserStatement.setLong(1, idClient);
            insertUserStatement.setLong(2, idAccount);

            insertUserStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE " + ACCOUNT + " SET id_Number = ?, type_account = ?, " +
                            "amount_money = ?, date_of_creation = ? WHERE id = ?");
            insertUserStatement.setString(1, String.valueOf(account.getIdNumber()));
            insertUserStatement.setString(2, account.getTypeAccount());
            insertUserStatement.setString(3, String.valueOf(account.getAmountOfMoney()));
            insertUserStatement.setString(4, account.getCreationDate());
            insertUserStatement.setString(5, String.valueOf(account.getId()));

            insertUserStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> getAccountsForClient(long clientId) {
        try {
            List<Account> accounts = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from account LEFT OUTER JOIN client_account ON " +
                    "account.id = client_account.account_id WHERE " +
                    "client_id = " + clientId;
            ResultSet accountResult = statement.executeQuery(fetchRoleSql);
            while (accountResult.next()) {
                Account newAccount = new AccountBuilder().setId(accountResult.getLong("id"))
                        .setIdNumber(accountResult.getLong("id_Number"))
                        .setTypeAccount(accountResult.getString("type_account"))
                        .setAmountOfMoney(accountResult.getInt("amount_money"))
                        .setCreationDate(accountResult.getString("date_of_creation"))
                        .build();
                accounts.add(newAccount);
            }
            return accounts;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public Account findAccountById(long id) {
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from account WHERE id = " + id;
            ResultSet accountResult = statement.executeQuery(fetchAccountSql);
            Account newAcc = new Account();
            while (accountResult.next()) {
                newAcc = new AccountBuilder().setId(accountResult.getLong("id"))
                    .setIdNumber(accountResult.getLong("id_Number"))
                    .setTypeAccount(accountResult.getString("type_account"))
                    .setAmountOfMoney(accountResult.getInt("amount_money"))
                    .setCreationDate(accountResult.getString("date_of_creation"))
                    .build();
            }
            return newAcc;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void deleteAccount(long id) {
        try {
            PreparedStatement deleteAccStatement = connection
                    .prepareStatement("DELETE FROM account WHERE id = " + id);
            deleteAccStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}
