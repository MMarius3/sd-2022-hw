package repository.account;

import controller.Response;
import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;
import static java.util.Collections.singletonList;

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
    public Account findByClientId(Long id) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `client_id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);
            accountResultSet.next();

            Account account=new AccountBuilder()
                    .setClient_id(accountResultSet.getLong("client_id"))
                    .setId(accountResultSet.getLong("id"))
                    .setBalance(accountResultSet.getLong("balance"))
                    .setType(accountResultSet.getString("type"))
                    .setCreated_at(LocalDate.parse(accountResultSet.getString("created_at")))
                    .build();
            return account;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    @Override
    public Account findById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);
            accountResultSet.next();

            Account account=new AccountBuilder()
                    .setClient_id(accountResultSet.getLong("client_id"))
                    .setId(accountResultSet.getLong("id"))
                    .setBalance(accountResultSet.getLong("balance"))
                    .setType(accountResultSet.getString("type"))
                    .setCreated_at(LocalDate.parse(accountResultSet.getString("created_at")))
                    .build();
            return account;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, account.getType());
            insertUserStatement.setLong(2, account.getBalance());
            insertUserStatement.setLong(3, account.getClient_id());
            insertUserStatement.setDate(4, Date.valueOf(LocalDate.now()));
            insertUserStatement.executeUpdate();

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
    public void removeAccount(Long account_id) {
        try {
            String sql = "DELETE from account where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,account_id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Long account_id,String account_type, Long new_balance) {
        try {
            String sql = "UPDATE account SET type = ?,balance = ? WHERE id= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,account_type);
            statement.setLong(2,new_balance);
            statement.setLong(3,account_id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByid(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(accountResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
    @Override
    public Response<Boolean> existsByCustomerId(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `client_id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(accountResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
}
