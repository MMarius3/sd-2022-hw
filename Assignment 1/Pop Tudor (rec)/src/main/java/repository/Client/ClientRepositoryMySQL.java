package repository.Client;

import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {

        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "Select * from client";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                clients.add(getClientFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdentificationNr(rs.getInt("IdentificationNr"))
                .setAddress(rs.getString("address"))
                .setPersonalNumericalCode(rs.getString("personalNumericalCode"))
                .build();
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        String sql = "Select * from client where id=" + id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return getClientFromResultSet(resultSet);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException | EntityNotFoundException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }

    @Override
    public boolean save(Client client) {
        String sql = "INSERT INTO client values (null, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setString(1, client.getName());
            insertStatement.setInt(2, client.getIdentificationNr());
            insertStatement.setString(3, client.getAddress());
            insertStatement.setString(4, client.getPersonalNumericalCode());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE from client where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(Long id) {
        String sql = "DELETE from client where id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        String sql = "UPDATE client SET name = ?, cardNumber = ?, address = ?, personalNumCode = ? WHERE id = " + client.getId();

        try {
            PreparedStatement updateClientStatement = connection.prepareStatement(sql);
            updateClientStatement.setString(1, client.getName());
            updateClientStatement.setLong(2, client.getIdentificationNr());
            updateClientStatement.setString(3, client.getAddress());
            updateClientStatement.setString(4, client.getPersonalNumericalCode());
            updateClientStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
