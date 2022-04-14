package repository.client;

import model.Client;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository{
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public ClientRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }
    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` ";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);

            while(clientResultSet.next()) {

                Client client = new Client();
                client.setId(clientResultSet.getLong("id"));
                client.setName(clientResultSet.getString("name"));
                client.setIdCardNumber(clientResultSet.getString("idCardNumber"));
                client.setCnp(clientResultSet.getString("cnp"));
                client.setAddress(clientResultSet.getString("address"));
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

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `name`=\'" + name +  "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Client client = new Client();
            client.setName(clientResultSet.getString("name"));
            client.setIdCardNumber(clientResultSet.getString("idCardNumber"));
            client.setCnp(clientResultSet.getString("cnp"));
            client.setAddress(clientResultSet.getString("address"));

            return client;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    @Override
    public Client findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `id`=\'" + id +  "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Client client = new Client();
            client.setName(clientResultSet.getString("name"));
            client.setIdCardNumber(clientResultSet.getString("idCardNumber"));
            client.setCnp(clientResultSet.getString("cnp"));
            client.setAddress(clientResultSet.getString("address"));

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
                    .prepareStatement("INSERT INTO client values (default , ?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertClientStatement.setString(1, client.getName());
            insertClientStatement.setString(2, client.getIdCardNumber());
            insertClientStatement.setString(3,client.getCnp());
            insertClientStatement.setString(4, client.getAddress());

            insertClientStatement.executeUpdate();

            ResultSet rs = insertClientStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

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
    public boolean removeById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql =  "Delete from `" + CLIENT + "` where `id`=\'" + id +  "\'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Client client){
        try {
            PreparedStatement insertClientStatement = connection
                    .prepareStatement( "UPDATE client SET name = ?, idCardNumber = ?, cnp = ?, address = ? where `id`=\'" + client.getId() +  "\'", Statement.RETURN_GENERATED_KEYS);
            insertClientStatement.setString(1, client.getName());
            insertClientStatement.setString(2, client.getIdCardNumber());
            insertClientStatement.setString(3,client.getCnp());
            insertClientStatement.setString(4, client.getAddress());

            insertClientStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
