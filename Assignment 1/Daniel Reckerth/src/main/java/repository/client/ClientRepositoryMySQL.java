package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.enums.TableTypeSQL.CLIENT;


public class ClientRepositoryMySQL implements ClientRepository {

  private final Connection connection;

  public ClientRepositoryMySQL(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Client> findAll() {
    String sql = "SELECT * FROM %s".formatted(CLIENT.getLabel());
    List<Client> clients = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      while(resultSet.next()) {
        clients.add(getClientFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return clients;
  }

  @Override
  public Optional<Client> findById(Long id) {
    String sql = "SELECT * FROM %s WHERE id = ?".formatted(CLIENT.getLabel());

    try {
      PreparedStatement findStatement = connection.prepareStatement(sql);
      findStatement.setLong(1, id);
      ResultSet resultSet = findStatement.executeQuery();
      if(resultSet.next()) {
        return Optional.of(getClientFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public boolean save(Client client) {
    String sql = "INSERT INTO %s VALUES(?, ?, ?, ?, ?)".formatted(CLIENT.getLabel());

    try {
      PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      if(client.getId() == null) {
        insertStatement.setNull(1, Types.NULL);
      } else {
        insertStatement.setLong(1, client.getId());
      }
      insertStatement.setString(2, client.getName());
      insertStatement.setString(3, client.getCardNumber());
      insertStatement.setString(4, client.getSSN());
      insertStatement.setString(5, client.getAddress());
      insertStatement.executeUpdate();

      ResultSet rs = insertStatement.getGeneratedKeys();
      rs.next();
      client.setId(rs.getLong(1));

      return true;
    } catch (SQLException throwables) {
      return false;
    }
  }

  @Override
  public boolean update(Long id, Client client) {
    String sql = "UPDATE %s SET name=?, card_number=?, ssn=?, address=? WHERE id=?".formatted(CLIENT.getLabel());
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, client.getName());
      preparedStatement.setString(2, client.getCardNumber());
      preparedStatement.setString(3, client.getSSN());
      preparedStatement.setString(4, client.getAddress());
      preparedStatement.setLong(5, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean deleteById(Long id) {
    String sql = "DELETE FROM %s WHERE ID = ?".formatted(CLIENT.getLabel());
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
    String sql = "DELETE FROM %s WHERE id >= 0".formatted(CLIENT.getLabel());

    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  private Client getClientFromResultSet(ResultSet resultSet) throws SQLException {
    return new ClientBuilder()
            .setId(resultSet.getLong("id"))
            .setName(resultSet.getString("name"))
            .setCardNumber(resultSet.getString("card_number"))
            .setSSN(resultSet.getString("ssn"))
            .setAddress(resultSet.getString("address"))
            .build();
  }
}
