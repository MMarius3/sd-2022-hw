package repository_layer.repository.account;

import bussiness_layer.builder.AccountBuilder;
import bussiness_layer.models.Account;
import bussiness_layer.models.User;
import repository_layer.repository.user.UserRepositoryMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository_layer.database_builder.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository {

  private final Connection connection;
  private final UserRepositoryMySQL userRepositoryMySQL;

  public AccountRepositoryMySQL(Connection connection)

  {
    this.connection = connection;
    this.userRepositoryMySQL = new UserRepositoryMySQL(connection);
  }

  @Override
  public boolean insert(Account account, String username) {
    User user = userRepositoryMySQL.findByUsername(username);
    if (user != null)
    {
      try {
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO " + ACCOUNT + " values (null, ?, ?, ?, ?)");
        insertStatement.setString(1, account.getType());
        insertStatement.setFloat(2, account.getAmount_of_money());
        insertStatement.setDate(3, new Date(System.currentTimeMillis()));
        insertStatement.setLong(4, user.getId());
        insertStatement.executeUpdate();

        return true;
      } catch (SQLException e) {
      }
      return false;
    }
   return false;
  }

  @Override
  public List<Account> getUserAccounts(String username) {
    User user = userRepositoryMySQL.findByUsername(username);
    if (user != null)
    {
      try {
        PreparedStatement findStatement = connection
                .prepareStatement("SELECT * FROM " + ACCOUNT + " WHERE `user_id` = "+ user.getId());

        ResultSet accountResultSet = findStatement.executeQuery();

        List<Account> accounts = new ArrayList<>();

        while (accountResultSet.next())
        {
          Account account = new AccountBuilder()
                  .setId(accountResultSet.getLong("id"))
                  .setType(accountResultSet.getString("type"))
                  .setAmountOfMoney(accountResultSet.getFloat("amount_of_money"))
                  .setDateOfCreation(accountResultSet.getDate("date_of_creation"))
                  .build();
          accounts.add(account);
        }

        return accounts;
      } catch (SQLException e) {
      }
      return null;
    }
    return null;
  }

  @Override
  public Account getById(Long id) {
    try {
      PreparedStatement findStatement = connection
              .prepareStatement("SELECT * FROM " + ACCOUNT + " WHERE `id` = "+ id);

      ResultSet accountResultSet = findStatement.executeQuery();

      accountResultSet.next();

      Account account = new AccountBuilder()
              .setId(accountResultSet.getLong("id"))
              .setType(accountResultSet.getString("type"))
              .setAmountOfMoney(accountResultSet.getFloat("amount_of_money"))
              .setDateOfCreation(accountResultSet.getDate("date_of_creation"))
              .build();

      return account;
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public boolean remove(Long id) {
    try {
      PreparedStatement deleteStatement = connection
              .prepareStatement("DELETE FROM " + ACCOUNT + " WHERE id =  ? ");
      deleteStatement.setLong(1, id);
      deleteStatement.executeUpdate();

      return true;
    } catch (SQLException e) {
    }
    return false;
  }

  @Override
  public boolean update(Long id, Account newAccount) {
    try {
      PreparedStatement updateStatement = connection
              .prepareStatement("UPDATE " + ACCOUNT +" SET type = ?,  amount_of_money = ? " +" WHERE id =  ? ");
      updateStatement.setString(1, newAccount.getType());
      updateStatement.setFloat(2, newAccount.getAmount_of_money());
      updateStatement.setLong(3, id);
      updateStatement.executeUpdate();

      return true;
    } catch (SQLException e) {
    }
    return false;
  }
}
