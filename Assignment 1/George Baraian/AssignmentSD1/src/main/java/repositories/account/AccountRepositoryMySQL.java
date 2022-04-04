package repositories.account;

import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;
    private final AccountRepository accountRepository;

    public AccountRepositoryMySQL(Connection connection, AccountRepository accountRepository) {
        this.connection = connection;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAllByClientId(Long clientId) {
        List<Account> accounts = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * from account where clientId=" + clientId;
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException{
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setAmount(rs.getLong("amount"))
                .setClientID(rs.getLong("clientId"))
                .build();
    }

    @Override
    public boolean save(Account account) {
        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?)");
            preparedStatement.setString(1, account.getType());
            preparedStatement.setLong(2, account.getAmount());
            preparedStatement.setLong(3,account.getClientID());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong(1);
            account.setId(accountId);

            accountRepository.save(account);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void deleteByClientId(Long clientId) {
        try{
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where clientId=" + clientId;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
