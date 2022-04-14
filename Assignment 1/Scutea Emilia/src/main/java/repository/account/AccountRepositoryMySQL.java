package repository.account;

import controller.Response;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static database.Constants.Tables.ACCOUNT;
import static java.util.Collections.singletonList;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection){
        this.connection=connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from `" + ACCOUNT + "` where `accountId`=\'" + id + "\'";
            ResultSet resultSet = statement.executeQuery(fetchUserSql);
            resultSet.next();

            return new AccountBuilder()
                    .setAccountId(resultSet.getLong("accountId"))
                    .setType(resultSet.getString("type"))
                    .setAmount(resultSet.getDouble("amount"))
                    .setDateOfCreation(resultSet.getDate("dateOfCreation"))
                    .build();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public Response<Boolean> existsById(Long id){
        try{
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from `" + ACCOUNT + "` where `accountId`=\'" + id + "\'";

            ResultSet resultSet = statement.executeQuery(fetchAccountSql);
            return new Response<>(resultSet.next());
        } catch (SQLException e){
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public boolean save(Account account) {
        try{
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO account values (null, ?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setDouble(2, account.getAmount());
            insertStatement.setDate(3, account.getDateOfCreation());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try{
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE account SET type =?, amount =? WHERE accountId =?");

            updateStatement.setString(1, account.getType());
            updateStatement.setDouble(2, account.getAmount());
            updateStatement.setLong(3, account.getAccountId());

            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try{
            Statement statement = connection.createStatement();
            String sql = "DELETE from account WHERE account.accountId >= 0";
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Double selectAmount(Long id) {
        try{
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select amount from `" + ACCOUNT + "` where `accountId`=\'" + id + "\'";

            ResultSet resultSet = statement.executeQuery(fetchAccountSql);
            resultSet.next();
            return resultSet.getDouble("amount");

        } catch (SQLException e){
            System.out.println(e.toString());
        }
        return null;
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setAccountId(rs.getLong("accountId"))
                .setType(rs.getString("type"))
                .setAmount(rs.getDouble("amount"))
                .setDateOfCreation(rs.getDate("dateOfCreation"))
                .build();
    }
}
