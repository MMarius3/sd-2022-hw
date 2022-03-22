package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.enums.AccountType;
import repository.client.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.enums.TableTypeSQL.ACCOUNT;
import static database.enums.TableTypeSQL.CLIENT;

public class AccountRepositoryMySQL implements AccountRepository {

  private final Connection connection;
  private final ClientRepository clientRepository;

  public AccountRepositoryMySQL(Connection connection, ClientRepository clientRepository) {
    this.connection = connection;
    this.clientRepository = clientRepository;
  }

  @Override
  public List<Account> findAll() {
    String sql = "SELECT * FROM %s".formatted(ACCOUNT.getLabel());
    List<Account> accounts = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      while(resultSet.next()) {
        accounts.add(getAccountFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return accounts;
  }



  @Override
  public Optional<Account> findById(Long id) {
    String sql = "SELECT * FROM %s WHERE id = ?".formatted(ACCOUNT.getLabel());

    try {
      PreparedStatement findStatement = connection.prepareStatement(sql);
      findStatement.setLong(1, id);
      ResultSet resultSet = findStatement.executeQuery();
      if(resultSet.next()) {
        return Optional.of(getAccountFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public boolean save(Account account) {
    // Q: Should a restriction be imposed that an account cannot be created without an existing client_id?
    String sql = "INSERT INTO %s VALUES(?, ?, ?, ?, ?)".formatted(ACCOUNT.getLabel());
    try {
      Long clientId = account.getClient().getId();
      final Optional<Client> clientOptional = clientRepository.findById(clientId);
      if(clientOptional.isPresent()) {
        PreparedStatement insertStatement = connection.prepareStatement(sql);
        if (account.getId() == null) {
          insertStatement.setNull(1, Types.NULL);
        } else {
          insertStatement.setLong(1, account.getId());
        }
        insertStatement.setString(2, account.getAccountType().getLabel());
        insertStatement.setDouble(3, account.getBalance());
        insertStatement.setDate(4, Date.valueOf(account.getCreationDate()));
        insertStatement.setLong(5, clientId);
        insertStatement.executeUpdate();
        return true;
      }
    } catch (SQLException | NullPointerException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean update(Long id, Account updatingAccount) {
    // Client must not be updated because an already given account cannot be given to another client
    String sql = "UPDATE %s SET acc_type=?, balance=?, creation_date=? WHERE id=?".formatted(ACCOUNT.getLabel());
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, updatingAccount.getAccountType().getLabel());
      preparedStatement.setDouble(2, updatingAccount.getBalance());
      preparedStatement.setDate(3, new java.sql.Date(updatingAccount.getCreationDate().toEpochDay()));
      preparedStatement.setLong(4, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean deleteById(Long id) {
    String sql = "DELETE FROM %s WHERE ID = ?".formatted(ACCOUNT.getLabel());
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public void deleteAll() {
    String sql = "DELETE FROM %s WHERE id >= 0".formatted(ACCOUNT.getLabel());

    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }


  @Override
  public List<Account> findAccountsByClientId(Long clientId) {
    String sql = "SELECT a.id, a.acc_type, a.balance, a.creation_date, a.client_id FROM %s a JOIN %s ON a.client_id = ?".formatted(ACCOUNT.getLabel(), CLIENT.getLabel());
    List<Account> accounts = new ArrayList<>();
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, clientId);
      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        accounts.add(getAccountFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return accounts;
  }

  private Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
    // Q: Is the responsibility of this method to check if the client with the client_id really exists?
    //    Should it assume that the Optional<Client> it gets is indeed a client?
    // Q: If it is its responsibility should allow a creation of client with the non-existing client_id?
    // If we impose the restriction that when creating an account we specify the client_id correctly, i.e verify
    // the client exists, then there is no need of checking here.
    final Account account = new AccountBuilder()
            .setId(resultSet.getLong("id"))
            .setAccountType(AccountType.valueOfLabel(resultSet.getString("acc_type")))
            .setBalance(resultSet.getDouble("balance"))
            .setCreationDate(resultSet.getDate("creation_date").toLocalDate())
            .build();

    final Optional<Client> clientOptional = clientRepository.findById(resultSet.getLong("client_id"));
    clientOptional.ifPresent(account::setClient);
    return account;
  }
}
