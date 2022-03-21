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
            String sql = "DELETE from client where id =" + Long.toString(id);
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account update(Account account) {
        return null;
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
