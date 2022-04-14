package repository.administrator_user;

import database.DatabaseConnectionFactory;
import dtos.AdministratorUserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AdministratorUserRepositoryMySQL implements AdministratorUserRepository<AdministratorUserDTO> {

    private final String TABLE_NAME = "Administrator_User";
    private Connection connection;

    public AdministratorUserRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public AdministratorUserRepositoryMySQL() {
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
    public long create(AdministratorUserDTO object) {
        return 0;
    }

    @Override
    public AdministratorUserDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(AdministratorUserDTO object) {

    }

    @Override
    public List<AdministratorUserDTO> getAll() {
        return null;
    }
}
