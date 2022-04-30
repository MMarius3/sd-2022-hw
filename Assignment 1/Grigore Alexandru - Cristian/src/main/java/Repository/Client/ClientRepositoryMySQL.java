package Repository.Client;

import Model.Builder.ClientBuilder;
import Model.Client;
import Repository.Security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;
    public ClientRepositoryMySQL(Connection connection){
        this.connection = connection;
    }


    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from clients";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                clients.add(getClientsFromResultSet(rs));
            }
            }catch (SQLException e){
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO clients values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setLong(2, client.getIdentityCardNumber());
            insertUserStatement.setString(3, client.getPersonalNumericalCode());
            insertUserStatement.setString(4, client.getAddress());
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
            String sql = "DELETE from clients where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client getClientsFromResultSet(ResultSet rs) throws SQLException{
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdentityCardNumber(rs.getLong("identityCardNumber"))
                .setPersonalNumericalCode(rs.getString("personalNumericalCode"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
