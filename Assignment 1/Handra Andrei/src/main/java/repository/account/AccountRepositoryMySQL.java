package repository.account;

import controller.Response;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.ACCOUNT;
import static java.util.Collections.singletonList;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql ="Select * from account";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                accounts.add(getAccountFromResultSet(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return accounts;
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setIdentificationNumber(rs.getLong("identificationNumber"))
                .setCardNumber(rs.getString("cardNumber"))
                .setType(rs.getString("type"))
                .setSumOfMoney(rs.getFloat("sumOfMoney"))
                .setCreationDate(rs.getDate("creationDate"))
                .build();
    }

    @Override
    public Optional<Account> findAccountByIdentificationNumber(Long identificationNumber) {
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `identificationNumber`=\'" + identificationNumber + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            Account account = new AccountBuilder()
                    .setIdentificationNumber(accountResultSet.getLong("identificationNumber"))
                    .setCardNumber(accountResultSet.getString("cardNumber"))
                    .setType(accountResultSet.getString("type"))
                    .setSumOfMoney(accountResultSet.getFloat("sumOfMoney"))
                    .setCreationDate(accountResultSet.getDate("creationDate"))
                    .build();

            return Optional.of(account);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Account account) {

        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO account values ( ?, ?, ?, ?, ?)");
            insertAccountStatement.setLong(1, account.getIdentificationNumber());
            insertAccountStatement.setString(2,account.getCardNumber());
            insertAccountStatement.setString(3, account.getType());
            insertAccountStatement.setFloat(4, account.getSumOfMoney());
            insertAccountStatement.setDate(5, new Date(account.getCreationDate().getTime()));
            insertAccountStatement.executeUpdate();

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
            String sql = "DELETE from account where identificationNumber >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Account account,String oldCardNumber) {
        try{
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE account set cardNumber = ?, type = ?, sumOfMoney = ? where cardNumber = ?");
            updateAccountStatement.setString(1, account.getCardNumber());
            updateAccountStatement.setString(2, account.getType());
            updateAccountStatement.setFloat(3, account.getSumOfMoney());
            updateAccountStatement.setString(4, oldCardNumber);

            updateAccountStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String cardNumber) {
        try{
            PreparedStatement deleteAccountStatement = connection.prepareStatement("DELETE from account where cardNumber = ?");
            deleteAccountStatement.setString(1,cardNumber);
            deleteAccountStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Account> findAccountByCardNumber(String cardNumber) {
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `cardNumber`=\'" + cardNumber + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            Account account = new AccountBuilder()
                    .setIdentificationNumber(accountResultSet.getLong("identificationNumber"))
                    .setCardNumber(accountResultSet.getString("cardNumber"))
                    .setType(accountResultSet.getString("type"))
                    .setSumOfMoney(accountResultSet.getFloat("sumOfMoney"))
                    .setCreationDate(accountResultSet.getDate("creationDate"))
                    .build();

            return Optional.of(account);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Optional.empty();
    }

    @Override
    public Response<Boolean> existsByCardNumber(String cardNumber) {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `cardNumber`=\'" + cardNumber + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            return new Response<>(accountResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
}
