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
    public ArrayList<Client> findAll() {
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
        return (ArrayList<Client>) clients;
    }

    @Override
    public Optional<Client> findById(Integer id) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("SELECT * from client where id=?");
            insertStatement.setInt(1, id);
            ResultSet rs = insertStatement.executeQuery();
            Client client = new Client();
            while(rs.next()){
                client = getClientFromResultSet(rs);
            }

            return Optional.of(client);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE client " +
                            "SET name=? , idNumber=? , cnp=? , address=? " +
                            "WHERE id = ?");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdNumber());
            insertStatement.setString(3, client.getCnp());
            insertStatement.setString(4, client.getAddress());
            insertStatement.setInt(5, client.getId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
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

    @Override
    public boolean removeAll() {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("delete from client");
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setIdNumber(rs.getString("idNumber"))
                .setCnp(rs.getString("cnp"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
