package repository.account;

import model.Account;
import model.User;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.USER;

public class AccountRepositoryMySQL implements AccountRepository{
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            Account account = Account.builder()
                    .id(accountResultSet.getLong("id"))
                    .number(accountResultSet.getString("number"))
                    .type(accountResultSet.getString("type"))
                    .money(accountResultSet.getInt("money"))
                    .date(accountResultSet.getDate("creation"))
                    .build();

            return account;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO " + ACCOUNT + " values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setString(1, account.getNumber());
            insertAccountStatement.setString(2, account.getType());
            insertAccountStatement.setInt(3, account.getMoney());
            insertAccountStatement.setDate(4, (Date) account.getDate());
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong(1);
            account.setId(accountId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean update(Long id, Account newEntity) {
        return false;
    }
}
