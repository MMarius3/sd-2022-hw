package repository.client;

import database.DatabaseConnectionFactory;
import dtos.ClientDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository<ClientDTO> {

    private final String TABLE_NAME = "Client";
    private Connection connection;

    public ClientRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public ClientRepositoryMySQL() {
        connection = DatabaseConnectionFactory.getProductionConnection();
    }

    @Override
    public void deleteByID(long id) {
        String sql = "DELETE * FROM " + TABLE_NAME + " WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM " + TABLE_NAME;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public long create(ClientDTO object) {

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, object.getName());
            insertStatement.setString(2, object.getIdentityCardNumber());
            insertStatement.setString(3, object.getPersonalNumericCode());
            insertStatement.setString(4, object.getPersonalNumericCode());

            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();
            rs.next();

            return rs.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ClientDTO getByID(long id) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSetToClientDTO(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(ClientDTO object) {

    }

    @Override
    public List<ClientDTO> getAll() {

        String sql = "SELECT * FROM " + TABLE_NAME;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<ClientDTO> clientDTOs = new ArrayList<>();

            while (resultSet.next()) {
                clientDTOs.add(resultSetToClientDTO(resultSet));
            }

            return clientDTOs;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    private ClientDTO resultSetToClientDTO(ResultSet resultSet) throws SQLException {

        return new ClientDTO()
                .setId(resultSet.getLong("id"))
                .setAddress(resultSet.getString("address"))
                .setName(resultSet.getString("name"))
                .setIdentityCardNumber(resultSet.getString("identity_card_number"))
                .setPersonalNumericCode(resultSet.getString("personal_numeric_code"));
    }
}
