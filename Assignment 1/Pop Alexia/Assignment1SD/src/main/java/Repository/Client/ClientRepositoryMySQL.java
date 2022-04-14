package Repository.Client;

import Controller.Response;
import Model.Builder.ClientBuilder;
import Model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Database.Constants.Tables.*;
import static java.util.Collections.singletonList;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ObservableList<Client> findAll() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from client where id >= 0";
            ResultSet clientResultSet = statement.executeQuery(sql);

            while(clientResultSet.next()) {
                Client client = new ClientBuilder()
                        .setId(clientResultSet.getLong("id"))
                        .setName(clientResultSet.getString("name"))
                        .setCardNr(clientResultSet.getLong("cardnr"))
                        .setPnc(clientResultSet.getLong("pnc"))
                        .setAddress(clientResultSet.getString("address"))
                        .build();

                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findByCardNr(Long cardnr) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `cardnr`=\'" + cardnr +"\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Client client = new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setName(clientResultSet.getString("name"))
                    .setCardNr(clientResultSet.getLong("cardnr"))
                    .setPnc(clientResultSet.getLong("pnc"))
                    .setAddress(clientResultSet.getString("address"))
                    .build();

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
                    "Select * from `" + CLIENT + "` where `id`=\'" + id +"\'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            clientResultSet.next();

            Client client = new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setName(clientResultSet.getString("name"))
                    .setCardNr(clientResultSet.getLong("cardnr"))
                    .setPnc(clientResultSet.getLong("pnc"))
                    .setAddress(clientResultSet.getString("address"))
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
                    .prepareStatement("INSERT INTO client values (null, ?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setLong(2, client.getCardnr());
            insertUserStatement.setLong(3,client.getPnc());
            insertUserStatement.setString(4,client.getAddress());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id , String name,Long pnc,String address) {
        Client c = findById(id);
        if(c == null) return false;
        if(name.equals("")) name = c.getName();
        if(pnc == -1 ) pnc = c.getPnc();
        if(address.equals("")) address = c.getAddress();

        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE client SET name = ? , pnc = ? , address =? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, name);
            insertUserStatement.setLong(2, pnc);
            insertUserStatement.setString(3, address);
            insertUserStatement.setLong(4, id);
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
    public Response<Boolean> existsByCardnr(Long cardNr) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `cardnr`=\'" + cardNr + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<Boolean>(singletonList(e.getMessage()));
        }
    }

    @Override
    public Response<Boolean> existsByPNC(Long pnc) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `pnc`=\'" + pnc + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<Boolean>(singletonList(e.getMessage()));
        }
    }

}