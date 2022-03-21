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
    public Optional<Account> viewAccount(Integer id) {
        try{
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM account WHERE id = ?");
            findStatement.setInt(1,id);
            ResultSet rs = findStatement.executeQuery();
            if(rs.next())
                return Optional.ofNullable(getAccountFromResultSet(rs));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean createAccount(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)");
            insertStatement.setInt(1,account.getClientId());
            insertStatement.setString(2, account.getIdentificationNumber());
            insertStatement.setString(3, account.getType());
            insertStatement.setString(4, account.getAmountOfMoney());
            insertStatement.setString(5, account.getDateOfCreation());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE account SET clientId = ?, identificationNumber = ?, type = ?, amountOfMoney = ?, dateOfCreation = ? WHERE id = ?" );
            insertStatement.setInt(1,account.getClientId());
            insertStatement.setString(2, account.getIdentificationNumber());
            insertStatement.setString(3, account.getType());
            insertStatement.setString(4, account.getAmountOfMoney());
            insertStatement.setString(5, account.getDateOfCreation());
            insertStatement.setInt(6,account.getId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteAccount(Integer id) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("DELETE FROM account WHERE id = ?");
            insertStatement.setInt(1,id);
            insertStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Account> findByClientID(Integer id){
        try{
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM account WHERE clientId = ?");
            findStatement.setInt(1,id);
            ResultSet rs = findStatement.executeQuery();
            if(rs.next())
                return Optional.ofNullable(getAccountFromResultSet(rs));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Account> viewAccounts() {
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

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getInt("id"))
                .setClientId(rs.getInt("clientId"))
                .setIdentificationNumber(rs.getString("identificationNumber"))
                .setType(rs.getString("type"))
                .setAmountOfMoney(rs.getString("amountOfMoney"))
                .setDateOfCreation(rs.getString("dateOfCreation"))
                .build();
    }

}
