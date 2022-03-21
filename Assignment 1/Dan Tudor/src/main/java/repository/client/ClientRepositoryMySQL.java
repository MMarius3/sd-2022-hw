package repository.client;

import model.Book;
import model.Client;
import model.builder.BookBuilder;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Client> findByNameInfo(String name) {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + CLIENT +"` where `name`=\'" + name + "\'";
            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()){
                 return Optional.of(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    @Override
    public boolean updateInfo(String name, Long cardId, String CNP, String address) {
        try{
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("Update `" + CLIENT +"` set `cardID`=\'" + cardId + "\', " + "`CNP`=\'" + CNP + "\', " + "`Address`=\'" + address
                            + "\' where `name`=\'" + name + "\'");
            insertClientStatement.executeUpdate();

            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Client> findByID(Long id) {
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + CLIENT +"` where `ID`=\'" + id + "\'";
            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()){
                return Optional.of(getClientFromResultSet(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    //public boolean create(String name, Long cardId, String CNP, String address, Long id, int balance, String type, Date date) {
    public boolean create(Client client) {
        try{
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setLong(2, client.getCardID());
            insertUserStatement.setString(3, client.getCNP());
            insertUserStatement.setString(4, client.getAddress());
            insertUserStatement.setInt(5, client.getBalance());
            insertUserStatement.setDate(6, new java.sql.Date(client.getDateOfCreation().getDate()));
            insertUserStatement.setString(7, client.getType());
            insertUserStatement.executeUpdate();

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAccount(Client client) {
        try{
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("Update `" + CLIENT +"` set `dateOfCreation`=\'" + new java.sql.Date(client.getDateOfCreation().getDate()) + "\', " + "`type`=\'" + client.getType() + "\', " + "`balance`=\'" + client.getBalance()
                            + "\' where `id`=\'" + client.getId() + "\'");
            insertClientStatement.executeUpdate();

            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void remove(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id =" + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateBalance(String name, int balance) {
        try{
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("Update `" + CLIENT +"` set `balance`=\'" + balance
                            + "\' where `name`=\'" + name + "\'");
            insertClientStatement.executeUpdate();

            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTransfer(String name1, String name2, int balance1, int balance2) {
        try{
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("Update `" + CLIENT +"` set `balance`=\'" + balance1
                            + "\' where `name`=\'" + name1 + "\'");
            insertClientStatement.executeUpdate();

            PreparedStatement insertClientStatement2 = connection
                    .prepareStatement("Update `" + CLIENT +"` set `balance`=\'" + balance2
                            + "\' where `name`=\'" + name2 + "\'");
            insertClientStatement2.executeUpdate();

            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setName(rs.getString("name"))
                .setCardID(rs.getLong("cardId"))
                .setID(rs.getLong("id"))
                .setCNP(rs.getString("cnp"))
                .setAddress(rs.getString("address"))
                .setBalance(rs.getInt("balance"))
                .setType(rs.getString("type"))
                .setDateOfCreation(rs.getDate("dateOfCreation"))
                .build();
    }

}
