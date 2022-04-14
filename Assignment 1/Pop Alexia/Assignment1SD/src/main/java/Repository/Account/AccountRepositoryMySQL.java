package Repository.Account;

import Controller.Response;
import Model.Account;
import Model.Builder.AccountBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Database.Constants.Tables.ACCOUNT;
import static java.util.Collections.singletonList;


public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ObservableList<Account> findAll() {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from account where accnr >= 0";
            ResultSet accountResultSet = statement.executeQuery(sql);

            while(accountResultSet.next()) {
                Account account = new AccountBuilder()
                        .setAccNr(accountResultSet.getLong("accnr"))
                        .setType(accountResultSet.getString("type"))
                        .setAmount(accountResultSet.getInt("amount"))
                        .setDate(accountResultSet.getDate("date"))
                        .build();

                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findByCardNr(Long cardnr) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `accnr`=\'" + cardnr +"\'";
            ResultSet accountResultSet = statement.executeQuery(fetchUserSql);
            accountResultSet.next();

            Account account = new AccountBuilder()
                    .setAccNr(accountResultSet.getLong("accnr"))
                    .setType(accountResultSet.getString("type"))
                    .setAmount(accountResultSet.getInt("amount"))
                    .setDate(accountResultSet.getDate("date"))
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
                    .prepareStatement("INSERT INTO account values (?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, account.getAccnr());
            insertUserStatement.setString(2, account.getType());
            insertUserStatement.setInt(3,account.getAmount());
            insertUserStatement.setDate(4,account.getDateOfCreation());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long accnr,String type , int amount) {

        Account a = findByCardNr(accnr);
        if(a == null) return false;
        if(type.equals("")) type = a.getType();
        if(amount == -1 ) amount = a.getAmount();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE account SET `type` = ? , amount = ? WHERE accnr = ?", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, type);
            insertUserStatement.setInt(2, amount);
            insertUserStatement.setLong(3,accnr);
            insertUserStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long cardnr) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("DELETE FROM account WHERE accnr = ?", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, cardnr);
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
            String sql = "DELETE from account where accnr >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean transfer(Long acc1, Long acc2, int amount) {
        Account ac1= findByCardNr(acc1);
        Account ac2 = findByCardNr(acc2);

        return update(acc1,"",ac1.getAmount()-amount) && update(acc2,"",ac2.getAmount()+amount);
    }

    @Override
    public boolean payBill(Long accountNr, int amount) {
        Account acc = findByCardNr(accountNr);
        return update(accountNr,"",acc.getAmount()-amount);
    }

    @Override
    public Response<Boolean> existsByCardnr(Long cardNr) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `accnr`=\'" + cardNr + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<Boolean>(singletonList(e.getMessage()));
        }
    }
}
