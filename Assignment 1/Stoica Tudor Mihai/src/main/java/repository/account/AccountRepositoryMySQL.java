package repository.account;

import database.DatabaseConnectionFactory;
import dtos.AccountDTO;
import dtos.ClientDTO;
import repository.client.ClientRepositoryMySQL;
import repository.currency.CurrencyRepositoryMySQL;

import java.sql.*;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository<AccountDTO> {

    private final String TABLE_NAME = "Account";

    private Connection connection;

    public AccountRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public AccountRepositoryMySQL() {
        connection = DatabaseConnectionFactory.getProductionConnection();
    }

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
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
        String sql = "DELETE * FROM " + TABLE_NAME;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public long create(AccountDTO object) {

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setLong(1, object.getClient().getId());
            insertStatement.setString(2, object.getIdentificationNumber());
            insertStatement.setFloat(3, object.getSum());
            insertStatement.setLong(4, object.getCurrency().getId());
            insertStatement.setDate(5, (Date) object.getCreationDate());

            insertStatement.executeUpdate();

            return insertStatement.getGeneratedKeys().getLong(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public AccountDTO getByID(long id) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.next();
            return resultSetToAccountDTO(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(AccountDTO object) {

        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "`client_id` = ?, " +
                "`identification_number` = ?, " +
                "`sum` = ?, " +
                "`currency_id` = ?, " +
                "`creation_date` = ?" +
                " WHERE id = ?";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setLong(1, object.getClient().getId());
            insertStatement.setString(2, object.getIdentificationNumber());
            insertStatement.setFloat(3, object.getSum());
            insertStatement.setLong(4, object.getCurrency().getId());
            insertStatement.setDate(5, (java.sql.Date) object.getCreationDate());
            insertStatement.setLong(6, object.getId());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AccountDTO> getAll() {
        String sql = "Select * from Account";
        return null;
    }


    private AccountDTO resultSetToAccountDTO(ResultSet resultSet) throws SQLException {

        CurrencyRepositoryMySQL currencyRepositoryMySQL = new CurrencyRepositoryMySQL();
        ClientRepositoryMySQL clientRepositoryMySQL = new ClientRepositoryMySQL();

        return new AccountDTO()
                .setId(resultSet.getLong("id"))
                .setIdentificationNumber(resultSet.getString("identification_number"))
                .setSum(resultSet.getFloat("sum"))
                .setCurrency(currencyRepositoryMySQL.getByID(resultSet.getLong("currency_id")))
                .setCreationDate(resultSet.getDate("creation_date"))
                .setClient(clientRepositoryMySQL.getByID(resultSet.getLong("client_id")));
    }
}
