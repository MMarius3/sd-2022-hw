package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.Optional;

import static database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository {
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account findByIdnumber(Long idnumber) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + ACCOUNT + "` where `idnumber`=\'" +idnumber+ "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            Account user = new AccountBuilder()
                    .setIdnumber(userResultSet.getLong("idnumber"))
                    .setType(userResultSet.getString("type"))
                    .setMoney(userResultSet.getLong("money"))
                    .setDateOfCreation(userResultSet.getString("dateOfCreation"))
                    .setClientID(userResultSet.getInt("clientID"))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)");
            insertStatement.setLong(1, account.getIdnumber());
            insertStatement.setString(2, account.getType());
            insertStatement.setLong(3, account.getMoney());
            insertStatement.setString(4, account.getDateOfCreation());
            insertStatement.setInt(5,account.getClientID());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteByIdnumber(Long idnumber) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where idnumber = \'" +idnumber+ "\'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateByIdnumber(Long idnumber, Account a2) {

        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE account SET idnumber =?, type=?, money=?, dateOfCreation=?, clientID=? WHERE idnumber = ?", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, a2.getIdnumber());
            insertUserStatement.setString(2,a2.getType());
            insertUserStatement.setLong(3,a2.getMoney());
            insertUserStatement.setString(4, a2.getDateOfCreation());
            insertUserStatement.setInt(5, a2.getClientID());
            insertUserStatement.setLong(6,idnumber);
            insertUserStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
