package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository{
    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Client> findAll(){
        List<Client> clients = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public boolean save(Client client) {
        try{
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setInt(2, client.getIdCardNumber());
            insertStatement.setInt(3, client.getPersNumCode());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        String sql = "UPDATE client SET name = ?, idCardNumber = ?, persNumCode = ?, address = ? WHERE id = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setInt(2, client.getIdCardNumber());
            preparedStatement.setInt(3, client.getPersNumCode());
            preparedStatement.setString(4, client.getAddress());
            preparedStatement.setLong(5, client.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException{
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setAddress(rs.getString("address"))
                .setIdCardNumber(rs.getInt("idCardNumber"))
                .setPersNumCode(rs.getInt("persNumCode"))
                .build();
    }
}
