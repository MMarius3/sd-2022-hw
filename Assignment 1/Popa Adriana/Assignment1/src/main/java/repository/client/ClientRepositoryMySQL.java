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
    public boolean addClient(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdentityCardNumber());
            insertStatement.setString(3, client.getPersonalNumericalCode());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Client> viewClients() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Optional<Client> findClient(Integer id) {
        try{
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM client WHERE id = ?");
            findStatement.setInt(1,id);
            ResultSet rs = findStatement.executeQuery();
            if(rs.next())
                return Optional.ofNullable(getClientFromResultSet(rs));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean updateClient(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE client SET name = ?, identityCardNumber = ?, personalNumericalCode = ?, address = ? WHERE id = ?" );
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdentityCardNumber());
            insertStatement.setString(3, client.getPersonalNumericalCode());
            insertStatement.setString(4, client.getAddress());
            insertStatement.setInt(5,client.getId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll(){
        String sql = "DELETE from client where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setIdentityCardNumber(rs.getString("identityCardNumber"))
                .setPersonalNumericCode(rs.getString("personalNumericalCode"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
