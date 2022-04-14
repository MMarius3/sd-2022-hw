package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
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
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("Select * from client where id = ?");
            insertStatement.setString(1, id.toString());
            ResultSet rs = insertStatement.executeQuery();
            rs.next();
            return Optional.of(getClientFromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setInt(2, client.getIdCardNumber());
            insertStatement.setLong(3, client.getIdCode());
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
                    .prepareStatement("UPDATE client SET name = ?, idCardNumber = ?, idCode = ? WHERE id = ?");
            insertStatement.setString(1, client.getName());
            insertStatement.setInt(2, client.getIdCardNumber());
            insertStatement.setLong(3, client.getIdCode());
            insertStatement.setLong(4, client.getId());
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
                .setIdCardNumber(rs.getInt("idCardNumber"))
                .setIdCode(rs.getLong("idCode"))
                .build();
    }

}