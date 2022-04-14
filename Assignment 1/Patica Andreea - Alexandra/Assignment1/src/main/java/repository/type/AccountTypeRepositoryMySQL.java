package repository.type;

import model.AccountType;

import java.sql.*;
import static database.Constants.Tables.ACCOUNT_TYPE;


public class AccountTypeRepositoryMySQL implements AccountTypeRepository{
    private  final Connection connection;

    public AccountTypeRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public AccountType findTypeById(Long id){
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT_TYPE +" where `id`=" + id;
            ResultSet rs = statement.executeQuery(fetchRoleSql);
            rs.next();
            return new AccountType(rs.getLong("id"), rs.getString("type"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addType(String type) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ACCOUNT_TYPE + " values (null, ?)");
            insertStatement.setString(1, type);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

}
