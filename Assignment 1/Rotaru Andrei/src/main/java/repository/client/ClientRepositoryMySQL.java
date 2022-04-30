package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.CLIENT;

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
            String sql = "Select * from `client`";
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
    public Notification<Client> findById(Long id) {
        Notification<Client> findByIdNotification = new Notification<>();
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from `" + CLIENT +"` WHERE `id`=\'" + id + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);

            if (clientResultSet.next()) {
                findByIdNotification.setResult(getClientFromResultSet(clientResultSet));
                return findByIdNotification;
            } else {
                findByIdNotification.addError("Invalid id client");
                return findByIdNotification;
            }
        }catch(SQLException e){
            e.printStackTrace();
            findByIdNotification.addError("Something is wrong with the Database");
        }
        return  findByIdNotification;
    }

    @Override
    public boolean save(Client client) {
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
    public boolean update(Client client) {
        try {
            PreparedStatement updateClientStatement = connection.
                    prepareStatement("UPDATE client SET `name`=?, `identity_card_number`=?," +
                            " `personal_numerical_code`=?, `address`=? WHERE `id` = ?");
            updateClientStatement.setString(1, client.getName());
            updateClientStatement.setString(2, client.getIdentityCardNumber());
            updateClientStatement.setString(3, client.getPersonalNumericalCode());
            updateClientStatement.setString(4, client.getAddress());
            updateClientStatement.setLong(5, client.getId());
            updateClientStatement.executeUpdate();
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

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdentityCardNumber(rs.getString("identity_card_number"))
                .setPersonalNumericalCode(rs.getString("personal_numerical_code"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
