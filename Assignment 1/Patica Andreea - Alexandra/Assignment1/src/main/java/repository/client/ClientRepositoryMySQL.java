package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryMySQL implements ClientRepository{
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
                clients.add(getClientsFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        Client client = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id =" + id;
            ResultSet rs = statement.executeQuery(sql);

            rs.next();
            client = getClientsFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(client);
    }

    @Override
    public Optional<Client> findByName(String name){
        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdNumber());
            insertStatement.setInt(3, client.getPersonalNumericalCode());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Client getClientsFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdNumber(rs.getString("idNumber"))
                .setPersonalNumericalCode(rs.getInt("personalNumericalCode"))
                .setAddress(rs.getString("address"))
//                .setAccount(new Date(rs.getDate("publishedDate").getTime()))                  TODO
                .build();
    }

}
