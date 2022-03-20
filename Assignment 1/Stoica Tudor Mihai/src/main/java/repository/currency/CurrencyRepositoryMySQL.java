package repository.currency;

import database.DatabaseConnectionFactory;
import dtos.CurrencyDTO;
import dtos.UserActionDTO;
import dtos.UserDTO;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CurrencyRepositoryMySQL implements CurrencyRepository<CurrencyDTO> {

    private final String TABLE_NAME = "Currency";
    private Connection connection;

    public CurrencyRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public CurrencyRepositoryMySQL() {
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
        String sql = "DELETE * FROM " + TABLE_NAME;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public long create(CurrencyDTO object) {
        return 0;
    }

    @Override
    public CurrencyDTO getByID(long id) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSetCurrencyDTO(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(CurrencyDTO object) {

    }

    @Override
    public List<CurrencyDTO> getAll() {
        return null;
    }

    private CurrencyDTO resultSetCurrencyDTO(ResultSet resultSet) throws SQLException {

        return new CurrencyDTO()
                .setId(resultSet.getLong("id"))
                .setCode(resultSet.getString("code"));
    }
}
