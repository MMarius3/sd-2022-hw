package repository.Client;

import controller.Response;
import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() throws SQLException {
        String sql = "SELECT * FROM client";

        List<Client> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                clients.add(getClientFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        String sql = "SELECT * FROM client WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Client client = getClientFromResultSet(resultSet);
                return Optional.of(client);
            } else {
                System.out.println("User with id: " + id + " not found");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        String sql = "INSERT INTO client values (null, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setString(1, client.getCardNumber());
            insertStatement.setString(2, client.getFullName());
            insertStatement.setString(3, client.getPnc());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        String sql = "UPDATE client SET cardNumber = ?, fullName = ?, pnc = ?, address = ? WHERE id = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getCardNumber());
            preparedStatement.setString(2, client.getFullName());
            preparedStatement.setString(3, client.getPnc());
            preparedStatement.setString(4, client.getAddress());
            preparedStatement.setLong(5, client.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Client client) {
        String sql = "DELETE FROM client WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE from client where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByField(String fieldName, String fieldValue) {
        String sql = "SELECT * FROM client WHERE " + fieldName + " = " + fieldValue;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return new Response<>(resultSet.next());
        } catch (SQLException e) {
            return new Response<>(Collections.singletonList(e.getMessage()));
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setID(rs.getLong("id"))
                .setFullName(rs.getString("fullName"))
                .setCardNumber(rs.getString("cardNumber"))
                .setPnc(rs.getString("pnc"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
