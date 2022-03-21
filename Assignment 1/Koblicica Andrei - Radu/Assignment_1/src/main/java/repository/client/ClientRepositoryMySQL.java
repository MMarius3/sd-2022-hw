package repository.client;

import controller.Response;
import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

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
        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdentityCardNumber());
            insertStatement.setString(3, client.getPersonalNumericalCode());
            insertStatement.setString(4, client.getAddress());
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

    @Override
    public Response<Boolean> existsPersonalNumericalCode(String personalNumericalCode) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `personalNumericalCode`=\'" + personalNumericalCode + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public Response<Boolean> existsIdentityCardNumber(String identityCardNumber) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `identityCardNumber`=\'" + identityCardNumber + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public boolean edit(Client client) {
        try {
            PreparedStatement editStatement = connection
                    .prepareStatement("UPDATE client SET name=?, identityCardNumber=?, personalNumericalCode=?, address=? WHERE id=?");
            editStatement.setString(1, client.getName());
            editStatement.setString(2, client.getIdentityCardNumber());
            editStatement.setString(3, client.getPersonalNumericalCode());
            editStatement.setString(4, client.getAddress());
            editStatement.setLong(5, client.getId());
            editStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdentityCardNumber(rs.getString("identityCardNumber"))
                .setPersonalNumericalCode(rs.getString("personalNumericalCode"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
