package repository.client;

import controller.Response;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;

import java.sql.*;
import java.time.LocalDate;
import static database.Constants.Tables.*;
import static java.util.Collections.singletonList;


public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;
    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void addClient(Client client) {
        try{
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getIdentityCardNumber());
            insertUserStatement.setString(3, client.getCNP());
            insertUserStatement.setString(4, client.getAddress());
            insertUserStatement.executeUpdate();

        }catch(SQLException e){

        }
    }

    @Override
    public void updateClientAddress(String cnp, String address) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE client SET address = ? WHERE CNP = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1, address);
            updateAccountStatement.setString(2, cnp);
            updateAccountStatement.executeUpdate();
        }catch (SQLException e){

        }
    }

    @Override
    public Client findClientByCnp(String cnp) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + CLIENT + " where `CNP`=\'" + cnp + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchAccountSql);
            clientResultSet.next();
            Long id = clientResultSet.getLong("id");
            String name = clientResultSet.getString("name");
            String identitycardnumber = clientResultSet.getString("identityCardNumber");
            String address = clientResultSet.getString("address");
            return new ClientBuilder().setCNP(cnp).setAddress(address).setName(name).setIdentityCardNumber(identitycardnumber).setId(id).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response<Boolean> existsByCNP(String CNP) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `CNP`=\'" + CNP + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }
}
