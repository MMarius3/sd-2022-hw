package repository.client;

import controller.Response;
import model.Account;
import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static java.util.Collections.singletonList;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        try {
            List<Client> clients = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + CLIENT;
            ResultSet clientResult = statement.executeQuery(fetchRoleSql);
            while (clientResult.next()) {
                Client newClient = new ClientBuilder().setId(clientResult.getLong("id"))
                        .setName(clientResult.getString("name"))
                        .setAddress(clientResult.getString("address"))
                        .setPNC(clientResult.getString("pnc"))
                        .setIdCardNumber(clientResult.getString("id_cardNr"))
                        .build();
                clients.add(newClient);
            }
            return clients;
        } catch (SQLException e) {

        }
        return null;
    }


    @Override
    public boolean addClient(Client client)  {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO "+ CLIENT + " values (null, ?, ?, ?, ?)");
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getAddress());
            insertUserStatement.setString(3, client.getPnc());
            insertUserStatement.setString(4, client.getIdCardNumber());

            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateClient(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE "+ CLIENT + " SET name = ?, address = ?, pnc = ?, id_cardNr = ? WHERE id = ?");
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getAddress());
            insertUserStatement.setString(3, client.getPnc());
            insertUserStatement.setString(4, client.getIdCardNumber());
            insertUserStatement.setString(5, client.getIdAsString());

            insertUserStatement.executeUpdate();

            //return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //return false;
        }
    }

    @Override
    public List<Account> getAccountsForClient() {
        return null;
    }

    @Override
    public Client findClientById(int id) {
        return null;
    }

    @Override
    public Response<Boolean> existsByPNC(String pnc) {

            try {
                Statement statement = connection.createStatement();

                String fetchUserSql =
                        "Select * from `" + CLIENT + "` where `pnc`=\'" + pnc + "\'";
                ResultSet userResultSet = statement.executeQuery(fetchUserSql);
                return new Response<>(userResultSet.next());
            } catch (SQLException e) {
                return new Response<>(singletonList(e.getMessage()));
            }
    }
}

