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

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Client findByCardNumber(Long cardNumber) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from `" + CLIENT + "` where `cardNumber`=\'" + cardNumber + "\'";
            ResultSet resultSet = statement.executeQuery(fetchUserSql);
            resultSet.next();

            return new ClientBuilder()
                    .setName(resultSet.getString("name"))
                    .setCardNumber(cardNumber)
                    .setPersonalNumericalCode(resultSet.getLong("personalNumericalCode"))
                    .setAddress(resultSet.getString("address"))
                    .setAccount(resultSet.getLong("accountId"))
                    .build();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Client client) {
        try{
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO client values (?, ?, ?, ?, ?)");
            insertStatement.setLong(1, client.getPersonalNumericalCode());
            insertStatement.setString(2, client.getName());
            insertStatement.setLong(3, client.getCardNumber());
            insertStatement.setString(4, client.getAddress());
            insertStatement.setLong(5, client.getAccount());
            insertStatement.executeUpdate();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client){
        try{
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE client SET personalNumericalCode =?, name =?, address =?, accountId =? WHERE cardNumber =?");
            updateStatement.setLong(1, client.getPersonalNumericalCode());
            updateStatement.setString(2, client.getName());
            updateStatement.setString(3, client.getAddress());
            updateStatement.setLong(4, client.getAccount());
            updateStatement.setLong(5, client.getCardNumber());
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try{
            Statement statement = connection.createStatement();
            String sql = "DELETE from client WHERE cardNumber >= 0";
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByCNP(Long cnp){
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from `" + CLIENT + "` where `personalNumericalCode`=\'" + cnp + "\'";

            ResultSet userResultSet = statement.executeQuery(fetchClientSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e){
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public Response<Boolean> existsByName(String name) {
        try{
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + CLIENT + "` where `name`=\'" + name + "\'";

            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e){
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setPersonalNumericalCode(rs.getLong("personalNumericalCode"))
                .setName(rs.getString("name"))
                .setCardNumber(rs.getLong("cardNumber"))
                .setAddress(rs.getString("address"))
                .setAccount(rs.getLong("accountId"))
                .build();
    }
}
