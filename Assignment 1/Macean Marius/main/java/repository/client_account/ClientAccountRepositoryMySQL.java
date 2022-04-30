package repository.client_account;

import java.sql.*;

public class ClientAccountRepositoryMySQL implements ClientAccountRepository {

    private final Connection connection;

    public ClientAccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addClientAccount(Long clientId, Long accountId) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client_account values (null, ?, ?)");
            insertStatement.setLong(1, clientId);
            insertStatement.setLong(2, accountId);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}