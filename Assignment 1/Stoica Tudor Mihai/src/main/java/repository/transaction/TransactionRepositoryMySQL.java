package repository.transaction;

import database.DatabaseConnectionFactory;
import dtos.TransactionDTO;

import java.sql.*;
import java.util.List;

public class TransactionRepositoryMySQL implements TransactionRepository<TransactionDTO> {

    private final String TABLE_NAME = "Transaction";
    private Connection connection;

    public TransactionRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public TransactionRepositoryMySQL() {
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
    public long create(TransactionDTO object) {

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setFloat(1, object.getSum());
            insertStatement.setDate(2, new Date(object.getDate().getTime()));
            insertStatement.setLong(3, object.getSenderAccount().getId());
            insertStatement.setLong(4, object.getReceiverAccount().getId());

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
    public TransactionDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(TransactionDTO object) {

    }

    @Override
    public List<TransactionDTO> getAll() {
        return null;
    }
}
