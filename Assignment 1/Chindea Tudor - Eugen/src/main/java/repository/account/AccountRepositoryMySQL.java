package repository.account;

import model.Account;
import model.Client;
import model.Role;
import model.builder.AccountBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static database.Constants.Tables.*;

public class AccountRepositoryMySQL implements  AccountRepository{

    private final Connection connection;
    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void addAccount(Account account) {
        try{
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, account.getType());
            insertUserStatement.setLong(2, account.getBalance());
            insertUserStatement.setLong(3, account.getClient_id());
            insertUserStatement.setDate(4, java.sql.Date.valueOf(account.getCreation()));
            insertUserStatement.executeUpdate();

        }catch(SQLException e){

        }

    }

    @Override
    public void payBill(Long id, Long amount) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + ACCOUNT + " where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            Long balance = accountResultSet.getLong("ballance");
            balance -= amount;
            if(balance >=0){
                PreparedStatement updateAccountStatement = connection
                        .prepareStatement("UPDATE account SET ballance = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
                updateAccountStatement.setLong(1, balance);
                updateAccountStatement.setLong(2, id);
                updateAccountStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void transferMoney(Long idFrom, Long idTo, Long amount) {
        Statement statement;
        Statement statement1;
        try {
            statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + ACCOUNT + " where `id`=\'" + idFrom + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();

            Long balanceFrom = accountResultSet.getLong("ballance");
            balanceFrom -= amount;
            if(balanceFrom >=0){
                PreparedStatement updateAccountStatement = connection
                        .prepareStatement("UPDATE account SET ballance = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
                updateAccountStatement.setLong(1, balanceFrom);
                updateAccountStatement.setLong(2, idFrom);
                updateAccountStatement.executeUpdate();
                statement1 = connection.createStatement();
                String fetchAccountSql1 = "Select * from " + ACCOUNT + " where `id`=\'" + idTo + "\'";
                ResultSet accountResultSet1 = statement.executeQuery(fetchAccountSql1);
                accountResultSet1.next();

                Long balanceTo = accountResultSet.getLong("ballance");
                balanceTo += amount;
                PreparedStatement updateAccountStatement1 = connection
                        .prepareStatement("UPDATE account SET ballance = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
                updateAccountStatement1.setLong(1, balanceTo);
                updateAccountStatement1.setLong(2, idTo);
                updateAccountStatement1.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccountBallance(Long id, Long ballance) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE account SET ballance = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setLong(1, ballance);
            updateAccountStatement.setLong(2, id);
            updateAccountStatement.executeUpdate();
        }catch (SQLException e){

        }
    }

    @Override
    public void deleteAccount(Long id) {
        try{
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where `id` = \'"+ id + "\'";
            statement.executeUpdate(sql);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Account findAccountById(Long id) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + ACCOUNT + " where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            accountResultSet.next();
            Long accountId = accountResultSet.getLong("id");
            String accType = accountResultSet.getString("type");
            Long balance = accountResultSet.getLong("ballance");
            Long client_id = accountResultSet.getLong("client_id");
            LocalDate creation = accountResultSet.getDate("client_id").toLocalDate();
            return new AccountBuilder().setId(accountId).setBallance(balance).setClientId(client_id).setType(accType).setCreationdate(creation).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Account> findAccountsForClient(Long clientid) {
        try {
            List<Account> accounts = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + accounts + " where `client_id`=\'" + clientid + "\'";
            ResultSet clientAccountResultSet = statement.executeQuery(fetchRoleSql);
            while (clientAccountResultSet.next()) {
                long accountId = clientAccountResultSet.getLong("client_id");
                accounts.add(findAccountById(accountId));
            }
            return accounts;
        } catch (SQLException e) {

        }
        return null;
    }
}
