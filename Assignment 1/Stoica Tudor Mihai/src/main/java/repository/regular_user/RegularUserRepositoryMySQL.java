package repository.regular_user;

import database.DatabaseConnectionFactory;
import dtos.RegularUserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RegularUserRepositoryMySQL implements RegularUserRepository<RegularUserDTO> {

    private final String TABLE_NAME = "Regular_User";
    private Connection connection;

    public RegularUserRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    public RegularUserRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public RegularUserRepositoryMySQL() {
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
    public long create(RegularUserDTO object) {
        return 0;
    }

    @Override
    public RegularUserDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(RegularUserDTO object) {

    }

    @Override
    public List<RegularUserDTO> getAll() {
        return null;
    }
}
