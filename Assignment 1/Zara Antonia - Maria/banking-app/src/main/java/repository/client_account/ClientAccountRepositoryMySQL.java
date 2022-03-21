package repository.client_account;

import model.Client;
import model.ClientAccount;
import model.builder.ClientAccountBuilder;
import model.builder.ClientBuilder;
import repository.client.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientAccountRepositoryMySQL implements ClientAccountRepository{

    private final Connection connection;
    private final ClientRepository clientRepository;

    public ClientAccountRepositoryMySQL(Connection connection, ClientRepository clientRepositoryMySQL) {
        this.connection = connection;
        this.clientRepository = clientRepositoryMySQL;
    }

    @Override
    public ArrayList<ClientAccount> findAll() {
        List<ClientAccount> clientAccounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client_account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clientAccounts.add(getClientAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<ClientAccount>) clientAccounts;
    }

    @Override
    public Optional<Client> findOwnerOfAccount(ClientAccount clientAccount){

        try {
            PreparedStatement sql = connection.prepareStatement("Select * from client_client_account where client_account_id = ?");
            sql.setInt(1, clientAccount.getIdentificationNumber());
            ResultSet rs = sql.executeQuery();
            Integer clientId = 0;
            while(rs.next()){
                clientId = rs.getInt("client_id");
            }

            Optional<Client> client  = clientRepository.findById(clientId);
            return client;

        } catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean removeAll() {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("delete from client_account");
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(ClientAccount clientAccount) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE client_account " +
                            "SET type=? , amount=? , creationDate=? " +
                            "WHERE id = ?");
            insertStatement.setString(1, clientAccount.getType());
            insertStatement.setDouble(2, clientAccount.getAmount());
            insertStatement.setDate(3, clientAccount.getCreationDate());
            insertStatement.setInt(4, clientAccount.getIdentificationNumber());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(ClientAccount clientAccount) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("DELETE FROM client_account " +
                            "WHERE id = ?");
            insertStatement.setInt(1, clientAccount.getIdentificationNumber());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(ClientAccount clientAccount, Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client_account values (null, ?, ?, ?)");
            insertStatement.setString(1, clientAccount.getType());
            insertStatement.setDouble(2, clientAccount.getAmount());
            insertStatement.setDate(3, clientAccount.getCreationDate());
            insertStatement.executeUpdate();

            PreparedStatement statement = connection.prepareStatement("Select * from client_account where type=?");
            statement.setString(1, clientAccount.getType());
            ResultSet rs = statement.executeQuery();
            System.out.println(statement.toString());
            Integer id = 0;
            while(rs.next()){
                id = rs.getInt("id");
            }

            PreparedStatement insertStatementJoin = connection
                    .prepareStatement("INSERT INTO client_client_account values (null, ?, ?)");
            insertStatementJoin.setInt(1, client.getId());
            insertStatementJoin.setInt(2, id);
            System.out.println(insertStatement.toString());
            insertStatementJoin.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ClientAccount getClientAccountFromResultSet(ResultSet rs) throws SQLException {
        ClientAccount clientAccount= new ClientAccountBuilder()
                .setIdentificationNumber(rs.getInt("id"))
                .setType(rs.getString("type"))
                .setAmount(rs.getDouble("amount"))
                .setCreationDate(rs.getDate("creationDate"))
                .build();

        if(findOwnerOfAccount(clientAccount).isPresent()){
            clientAccount.setOwner(findOwnerOfAccount(clientAccount).get());
        }
        else{
            clientAccount.setOwner(new Client());
        }

        return clientAccount;
    }
}
