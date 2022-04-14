package repository.client;

import controller.Response;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;
    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from " + CLIENT;
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            List<Client> clients = new ArrayList<>();
            while(clientResultSet.next()) {
                Client client = new ClientBuilder()
                        .setId(clientResultSet.getLong("id"))
                        .setName(clientResultSet.getString("name"))
                        .setCardId(clientResultSet.getString("cardId"))
                        .setAddress(clientResultSet.getString("address"))
                        .setPersonalNumericalCode(clientResultSet.getString("personalNumericalCode"))
                        .build();
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public Client findByName(String name) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `name`=\'" + name + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();

            Client client = new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setName(clientResultSet.getString("name"))
                    .setCardId(clientResultSet.getString("cardId"))
                    .setAddress(clientResultSet.getString("address"))
                    .setPersonalNumericalCode(clientResultSet.getString("personalNumericalCode"))
                    .build();

            return client;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("INSERT INTO `" + CLIENT +"` values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertClientStatement.setString(1, client.getName());
            insertClientStatement.setString(2, client.getCardId());
            insertClientStatement.setString(3, client.getPersonalNumericalCode());
            insertClientStatement.setString(4, client.getAddress());
            insertClientStatement.executeUpdate();

            ResultSet rs = insertClientStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);

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
            String sql = "DELETE from `" + CLIENT +"` where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByName(String name) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `name`=\'" + name + "\'";
            ResultSet userClientSet = statement.executeQuery(fetchClientSql);
            return new Response<>(userClientSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
}
