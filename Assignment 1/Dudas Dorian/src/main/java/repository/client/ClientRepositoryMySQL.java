package repository.client;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
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
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("Select * from client where id = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                return Optional.of(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getFullName());
            insertStatement.setString(2, client.getIdNumber());
            insertStatement.setString(3, client.getCnp());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateById(Long id, Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE client SET full_name=?, id_number=?, cnp=?, address=? WHERE id=?");

            insertStatement.setString(1, client.getFullName());
            insertStatement.setString(2, client.getIdNumber());
            insertStatement.setString(3, client.getCnp());
            insertStatement.setString(4, client.getAddress());
            insertStatement.setLong(5, id);
            insertStatement.executeUpdate();
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
                .setFullName(rs.getString("full_name"))
                .setIdNumber(rs.getString("id_number"))
                .setCNP(rs.getString("cnp"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
