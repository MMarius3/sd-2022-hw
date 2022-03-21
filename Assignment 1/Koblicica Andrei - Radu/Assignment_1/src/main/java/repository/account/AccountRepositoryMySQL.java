package repository.account;

import controller.Response;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.CLIENT;
import static java.util.Collections.singletonList;

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
                Account account=getAccountFromResultSet(rs);
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)");
            insertStatement.setLong(1, account.getClientId());
            insertStatement.setString(2, account.getIdentificationNumber());
            insertStatement.setString(3, account.getType().getText());
            insertStatement.setFloat(4, account.getAmount());
            insertStatement.setDate(5, account.getDateOfCreation());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(Account account){
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("DELETE FROM account WHERE id=?");
            insertStatement.setLong(1, account.getId());
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

    @Override
    public Response<Boolean> existsIdentificationNumber(String identificationNumber) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `identificationNumber`=\'" + identificationNumber + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public boolean edit(Account account) {
        try {
            PreparedStatement editStatement = connection
                    .prepareStatement("UPDATE account SET client_id=?, identificationNumber=?, type=?, amount=?, dateOfCreation=? WHERE id=?");
            editStatement.setLong(1, account.getClientId());
            editStatement.setString(2, account.getIdentificationNumber());
            editStatement.setString(3, account.getType().getText());
            editStatement.setFloat(4, account.getAmount());
            editStatement.setDate(5, account.getDateOfCreation());
            editStatement.setLong(6, account.getId());
            editStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existsClientId(String clientId) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "SELECT COUNT(id) FROM `" + CLIENT + "` where `id`=\'" + clientId + "\'";
            ResultSet resultSet = statement.executeQuery(fetchUserSql);
            if(resultSet.next()){
                int count=resultSet.getInt("COUNT(id)");
                if(count==1){
                    return true;
                }
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean existsAccountId(String accountId) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "SELECT COUNT(id) FROM `" + ACCOUNT + "` where `id`=\'" + accountId + "\'";
            ResultSet resultSet = statement.executeQuery(fetchUserSql);
            if(resultSet.next()){
                int count=resultSet.getInt("COUNT(id)");
                if(count==1){
                    return true;
                }
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean validSum(float sum, Long from) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "SELECT amount FROM `" + ACCOUNT + "` where `id`=\'" + from + "\'";
            ResultSet resultSet = statement.executeQuery(fetchUserSql);
            if(resultSet.next()){
                float amount=resultSet.getFloat("amount");
                if(amount>=sum){
                    return true;
                }
                return false;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateAmount(float amount, Long id){
        try {

            PreparedStatement statement = connection
                    .prepareStatement("UPDATE account SET amount = amount + ? WHERE id=?");
            statement.setFloat(1,amount);
            statement.setLong(2,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setClientId(rs.getLong("client_id"))
                .setIdentificationNumber(rs.getString("identificationNumber"))
                .setType(rs.getString("type"))
                .setAmount(rs.getFloat("amount"))
                .setDateOfCreation(new Date(rs.getDate("dateOfCreation").getTime()))
                .build();
    }
}
