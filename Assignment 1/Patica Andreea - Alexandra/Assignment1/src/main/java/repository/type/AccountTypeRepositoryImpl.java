package repository.type;

import model.AccountType;
import model.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.Constants.Tables.ROLE;

public class AccountTypeRepositoryImpl implements AccountTypeRepository{
    private  final Connection connection;

    public AccountTypeRepositoryImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public AccountType findTypeById(Long id){
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from account_type where `id`=" + id;
            ResultSet rs = statement.executeQuery(fetchRoleSql);
            rs.next();
            return new AccountType(rs.getLong("id"), rs.getString("type"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
