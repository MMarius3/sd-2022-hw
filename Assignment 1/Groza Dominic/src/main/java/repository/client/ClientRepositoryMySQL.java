package repository.client;

import controller.Response;
import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static java.util.Collections.singletonList;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;


    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;

    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client findByNumericalCode(String numericalCode) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `personal_numerical_code`=\'" + numericalCode + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Client client = new ClientBuilder()
                    .setIdCardNumber(clientResultSet.getString("id_card_number"))
                    .setPersonalNumericalCode(clientResultSet.getString("personal_numerical_code"))
                    .setAddress(clientResultSet.getString("address"))
                    .setCreationDate(LocalDate.parse(clientResultSet.getString("created_at")))
                    .setName(clientResultSet.getString("name"))
                    .setId(Long.parseLong(clientResultSet.getString("id")))
                    .build();
            return client;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    public Client findById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `id`=\'" + id + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Client client = new ClientBuilder()
                    .setIdCardNumber(clientResultSet.getString("id_card_number"))
                    .setPersonalNumericalCode(clientResultSet.getString("personal_numerical_code"))
                    .setAddress(clientResultSet.getString("address"))
                    .setCreationDate(LocalDate.parse(clientResultSet.getString("created_at")))
                    .setName(clientResultSet.getString("name"))
                    .setId(Long.parseLong(clientResultSet.getString("id")))
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
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getId_card_number());
            insertUserStatement.setString(3, client.getPersonal_numerical_code());
            insertUserStatement.setString(4, client.getAddress());
            insertUserStatement.setDate(5, Date.valueOf(LocalDate.now()));
            insertUserStatement.executeUpdate();

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
    public void removeClient(String personal_numerical_code) {
        try {
            Client client=findByNumericalCode(personal_numerical_code);
            String sql = "DELETE from client where id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClient( String name, String id_card_number, String personal_numerical_code, String address) {
        try {
            Client client=findByNumericalCode(personal_numerical_code);
            String sql = "UPDATE client SET name = ?,id_card_number = ?,personal_numerical_code=?,address=? WHERE id= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,id_card_number);
            statement.setString(3,personal_numerical_code);
            statement.setString(4,address);
            statement.setLong(5,client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByNumericalCode(String numericalCode) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `personal_numerical_code`=\'" + numericalCode + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
    @Override
    public Response<Boolean> existsById(String id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `id`=\'" + id + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(clientResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

}
