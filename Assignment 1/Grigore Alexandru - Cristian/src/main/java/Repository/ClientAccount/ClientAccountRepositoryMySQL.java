package Repository.ClientAccount;

import Model.Builder.ClientAccountBuilder;
import Model.Client;
import Model.ClientAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientAccountRepositoryMySQL implements ClientAccountRepository{

    private final Connection connection;
    public ClientAccountRepositoryMySQL(Connection connection){ this.connection = connection;}

    @Override
    public List<ClientAccount> findAll() {
        List<ClientAccount> clientAccounts = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from client_accounts";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                clientAccounts.add(getClientAccountsFromResultSet(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return clientAccounts;
    }

    @Override
    public Optional<ClientAccount> findByIdentificationNumber(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean save(ClientAccount clientAccount) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client_accounts values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, clientAccount.getId());
            insertUserStatement.setLong(2, clientAccount.getIdentificationNumber());
            insertUserStatement.setString(3, clientAccount.getType());
            insertUserStatement.setInt(4, clientAccount.getMoney());
            insertUserStatement.setDate(5, new java.sql.Date (clientAccount.getCreationDate().getTime()));
            insertUserStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client_accounts where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ClientAccount getClientAccountsFromResultSet(ResultSet rs) throws SQLException{
        return new ClientAccountBuilder()
                .setId(rs.getLong("id"))
                .setIdentificationNumber(rs.getLong("identificationNumber"))
                .setType(rs.getString("type"))
                .setMoney(rs.getInt("money"))
                .setCreationDate(rs.getDate("creationDate"))
                .build();
    }
}
