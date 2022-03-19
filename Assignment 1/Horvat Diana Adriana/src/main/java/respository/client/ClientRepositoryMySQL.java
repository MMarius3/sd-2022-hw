package respository.client;

import controller.Response;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;
    public static final String CLIENT = "clients";

    public ClientRepositoryMySQL (Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Client> findAll(){
        try {
            Statement statement = connection.createStatement();

            String getAllClientsSql =
                    "Select * from `" + CLIENT + "`";
            ResultSet clientResultSet = statement.executeQuery(getAllClientsSql);

            List<Client> clients = new ArrayList<>();

            while(clientResultSet.next()){
                Client client = new ClientBuilder()
                        .setName(clientResultSet.getString("name"))
                        .setIdCardNr(clientResultSet.getLong("ID_card_nr"))
                        .setPNC(clientResultSet.getLong("PNC"))
                        .setAddress(clientResultSet.getString("address"))
                        .setEmail(clientResultSet.getString("email"))
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
    public Client findByPNC(Long PNC){
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `PNC`=\'" + PNC + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();

            Client client = new ClientBuilder()
                    .setName(clientResultSet.getString("name"))
                    .setIdCardNr(clientResultSet.getLong("ID_card_nr"))
                    .setPNC(clientResultSet.getLong("PNC"))
                    .setAddress(clientResultSet.getString("address"))
                    .setEmail(clientResultSet.getString("email"))
                    .build();

            return client;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Client client){
        try {
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("INSERT INTO clients values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertClientStatement.setString(1, client.getName());
            insertClientStatement.setLong(2, client.getIdCardNr());
            insertClientStatement.setLong(3, client.getPNC());
            insertClientStatement.setString(4, client.getAddress());
            insertClientStatement.setString(5, client.getEmail());
            insertClientStatement.executeUpdate();

            ResultSet rs = insertClientStatement.getGeneratedKeys();
            rs.next();

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
            String sql = "DELETE from clients where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByPNC(Long PNC) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `PNC`=\'" + PNC + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public Response<Boolean> existsByIdCardNr(Long idCardNr) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `ID_card_nr`=\'" + idCardNr + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public Response<Boolean> existsByEmail(String email) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `email`=\'" + email + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public boolean updateClient(Client client){
        try {
            String fetchClientSql =
                    "UPDATE clients SET name = ?, ID_card_nr = ?,address=?,email=? WHERE id= ?";
            PreparedStatement statement = connection.prepareStatement(fetchClientSql);
            statement.setString(1, client.getName());
            statement.setLong(2, client.getIdCardNr());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getEmail());
            statement.setInt(5, client.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
