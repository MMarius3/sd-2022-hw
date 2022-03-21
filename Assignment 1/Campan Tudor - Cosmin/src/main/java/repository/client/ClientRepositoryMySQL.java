package repository.client;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.Optional;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.USER;

public class ClientRepositoryMySQL implements ClientRepository {
    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client findByName(String name) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + CLIENT + "` where `name`=\'" +name+ "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            Client user = new ClientBuilder()
                    .setName(userResultSet.getString("name"))
                    .setIdcardnumber(userResultSet.getLong("idcardnumber"))
                    .setCnp(userResultSet.getLong("cnp"))
                    .setAddress(userResultSet.getString("address"))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }


    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setLong(2, client.getIdcardnumber());
            insertStatement.setLong(3, client.getCnp());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteByName(String name) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where name = \'" +name+ "\'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateByName(String name,Client c2) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE client SET name =?, idcardnumber=?, cnp=?, address=? WHERE name = ?", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, c2.getName());
            insertUserStatement.setLong(2,c2.getIdcardnumber());
            insertUserStatement.setLong(3,c2.getCnp());
            insertUserStatement.setString(4, c2.getAddress());
            insertUserStatement.setString(5, name);
            insertUserStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
