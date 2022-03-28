package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts= new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean save(Account account) {
        try{
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO account values (null, ?, ?, ?)");
        insertStatement.setString(1, account.getType());
        insertStatement.setLong(2, account.getAmount());
        insertStatement.setDate(3, new java.sql.Date(account.getDateOfCreation().getTime()));
        insertStatement.executeUpdate();
        return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeById(long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id =" + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE account SET type = ?, amount = ? WHERE id = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getType());
            preparedStatement.setLong(2, account.getAmount());
            preparedStatement.setLong(3, account.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean transferMoney(long senderId, long receiverId, long amount) {
        String sentFrom = "UPDATE account SET amount = ? WHERE id = ?";
        String sentTo = "UPDATE account SET amount = ? WHERE id = ?";
        String selectSender = "SELECT * FROM account WHERE id=?";
        String selectReceiver = "SELECT * FROM account WHERE id=?";

        try{
            PreparedStatement statementSender = connection.prepareStatement(selectSender);
            statementSender.setLong(1,senderId);
            PreparedStatement statementReceiver = connection.prepareStatement(selectReceiver);
            statementSender.setLong(1,receiverId);
            ResultSet rsSender = statementSender.executeQuery();
            ResultSet rsReceiver = statementReceiver.executeQuery();

            PreparedStatement preparedStatementSent = connection.prepareStatement(sentFrom);
            PreparedStatement preparedStatementReceived = connection.prepareStatement(sentTo);

            preparedStatementSent.setLong(1, rsSender.getLong("amount") - amount);
            preparedStatementReceived.setLong(1,rsReceiver.getLong("amount") + amount);
            preparedStatementSent.setLong(2,rsSender.getLong("id"));
            preparedStatementReceived.setLong(2,rsReceiver.getLong("id"));
            preparedStatementSent.executeUpdate();
            preparedStatementReceived.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setAmount(rs.getLong("amount"))
                .setType(rs.getString("type"))
                .setDateOfCreation(new Date(rs.getDate("dateOfCreation").getTime()))
                .build();
    }
}
