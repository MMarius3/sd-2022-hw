package repository.client;

import controller.Response;
import model.Client;

import model.builder.ClientBuilder;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.CLIENT;

import static java.util.Collections.singletonList;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql ="Select * from client";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                clients.add(getClientFromResultSet(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return clients;
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setCNP(rs.getString("cnp"))
                .setCardNumber(rs.getString("cardNumber"))
                .setAddress(rs.getString("address"))
                .build();
    }

    @Override
    public Optional<Client> findById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchClientSql);
            accountResultSet.next();

            Client client = new ClientBuilder()
                    .setId(accountResultSet.getLong("id"))
                    .setName(accountResultSet.getString("name"))
                    .setCNP(accountResultSet.getString("cnp"))
                    .setCardNumber(accountResultSet.getString("cardNumber"))
                    .setAddress(accountResultSet.getString("address"))
                    .build();

            return Optional.of(client);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertClientStatement.setString(1, client.getName());
            insertClientStatement.setString(2, client.getCardNumber());
            insertClientStatement.setString(3, client.getCnp());
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
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Client client) {
        try{
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE client set name = ?, cardNumber = ?, cnp = ?, address = ? where id = ?");
            updateClientStatement.setString(1, client.getName());
            updateClientStatement.setString(2, client.getCardNumber());
            updateClientStatement.setString(3, client.getCnp());
            updateClientStatement.setString(4, client.getAddress());
            updateClientStatement.setLong(5,client.getId());

            updateClientStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Response<Boolean> existsByCnp(String cnp) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `cnp`=\'" + cnp + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public Response<Boolean> existsByIdCardNumber(String idCardNumber) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `cardNumber`=\'" + idCardNumber + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
}
