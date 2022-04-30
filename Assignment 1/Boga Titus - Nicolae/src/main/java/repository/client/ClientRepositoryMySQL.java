package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryMySQL implements ClientRepository {

  private final Connection connection;

  public ClientRepositoryMySQL(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Client> findAll() {
    List<Client> clients = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      String sql = "Select * from client";
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        clients.add(getClientFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return clients;
  }

  @Override
  public Optional<Client> findById(Long id) {

    Optional<Client> client = null;
    try {
      Statement statement = connection.createStatement();
      String sql = "Select * from client where id =" + id;
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {

        client= Optional.ofNullable(getClientFromResultSet(rs));
     //   client getClientFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
    return client;

  }

  @Override
  public boolean save(Client client) {
    try {
      PreparedStatement insertStatement = connection
          .prepareStatement("INSERT INTO client values (null, ?, ?, ?)");
      insertStatement.setString(1, client.getName());
      insertStatement.setString(2, client.getCnp());
      insertStatement.setString(3,client.getAddress());
      insertStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean update(Client client) {
    try {
      PreparedStatement insertStatement = connection
              .prepareStatement("UPDATE client SET name=?, cnp=?, address=? where id =?");
      insertStatement.setString(1, client.getName());
      insertStatement.setString(2, client.getCnp());
      insertStatement.setString(3,client.getAddress());
      insertStatement.setInt(4, Math.toIntExact(client.getId()));
      insertStatement.executeUpdate();
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
      String sql = "DELETE from client where id >= 0";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private Client getClientFromResultSet(ResultSet rs) throws SQLException {
    return new ClientBuilder()
        .setId(rs.getLong("id"))
        .setName(rs.getString("name"))
        .setCnp(rs.getString("cnp"))
        .setAddress(rs.getString("address"))
        .build();
       // .setPublishedDate(new Date(rs.getDate("publishedDate").getTime()))

  }

}
