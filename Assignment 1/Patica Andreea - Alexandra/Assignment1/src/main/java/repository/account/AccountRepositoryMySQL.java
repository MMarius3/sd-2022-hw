package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.type.AccountTypeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{
    private final Connection connection;
    private final AccountTypeRepository accountTypeRepository;

    public AccountRepositoryMySQL(AccountTypeRepository accountTypeRepository, Connection connection){
        this.connection = connection;
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    public List<Account> findByClientId(Long id){
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where client =" + id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountsFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?)");
            insertStatement.setInt(1, account.getClient().getId().intValue());
            insertStatement.setInt(2, account.getType().getId().intValue());
            insertStatement.setInt(3, account.getBalance());
            insertStatement.setDate(4, new Date(account.getCreationDate().getTime()));

            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        return false;
    }

    @Override
    public boolean updateBalance(Account account, Long balance) {
        try {
            String sql = "UPDATE account SET balance = " + balance + "WHERE id = " + account.getId();
            PreparedStatement updateStatement = connection
                    .prepareStatement(sql);
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account account) {
        try {
            String sql = "DELETE FROM account " + "WHERE id = " + account.getId();
            PreparedStatement updateStatement = connection
                    .prepareStatement(sql);
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Account getAccountsFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getInt("id"))
                .setType(accountTypeRepository.findTypeById(rs.getLong("type")))
                .setBalance(rs.getInt("balance"))
                .setCreationDate(new Date(rs.getDate("creationDate").getTime()))
                .build();
    }
}
