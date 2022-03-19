package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.USER;

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
                Long id = clientResultSet.getLong("id");
                String name = clientResultSet.getString("name");
                String cnp = clientResultSet.getString("CNP");
                String address = clientResultSet.getString("address");

                Client client = new ClientBuilder()
                        .setId(id)
                        .setName(name)
                        .setCNP(cnp)
                        .setAddress(address)
                        .build();
                clients.add(client);
            }
            return clients;

        } catch (SQLException e) {
            System.out.println("Error in find all");
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Client findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `id`=\'" + id + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();
            return new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setName(clientResultSet.getString(2))
                    .setCNP(clientResultSet.getString(3))
                    .setAddress(clientResultSet.getString(4))
                    .build();
        } catch (SQLException e) {
            System.out.println("Error in find by id");
            System.out.println(e);
        }
        return null;
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO " + CLIENT + " values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getCNP());
            insertUserStatement.setString(3, client.getAddress());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            client.setId(userId);

            return true;
        } catch (SQLException e) {
            System.out.println("Error in save");
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + USER + "` where `id`=\'" + id + "\'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Error in delete");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Client newClient) {
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE " + CLIENT + " set name = ?, cnp = ?, address = ? WHERE `id`=\'" + id + "\';", Statement.RETURN_GENERATED_KEYS);
            updateClientStatement.setString(1, newClient.getName());
            updateClientStatement.setString(2, newClient.getCNP());
            updateClientStatement.setString(3, newClient.getAddress());
            updateClientStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error in update");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Client findByName(String name) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `name`=\'" + name + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();

            return new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setName(clientResultSet.getString("name"))
                    .setCNP(clientResultSet.getString("CNP"))
                    .setCNP(clientResultSet.getString("address"))
                    .build();
        } catch (SQLException e) {
            System.out.println("Error in find by name");
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Client findByCNP(String cnp) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `CNP`=\'" + cnp + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            if(!clientResultSet.next()) {
                return null;
            }

            return new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setName(clientResultSet.getString("name"))
                    .setCNP(clientResultSet.getString("CNP"))
                    .setAddress(clientResultSet.getString("address"))
                    .build();
        } catch (SQLException e) {
            System.out.println("Error in find by cnp");
            System.out.println(e);
        }
        return null;
    }
}
