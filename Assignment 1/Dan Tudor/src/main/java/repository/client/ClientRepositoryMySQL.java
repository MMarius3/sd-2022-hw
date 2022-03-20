package repository.client;

import model.Book;
import model.Client;
import model.builder.BookBuilder;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
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
//            Statement statement = connection.createStatement();
//            String sql = "Update `" + CLIENT +"` set `cardID=`\'" + cardId + "\', " + "`CNP=`\'" + "\', " + "`Address=`\'" + address
//                    + "\' where `name`=\'" + name + "\'";
//            ResultSet rs = statement.executeQuery(sql);
//            System.out.println(name+cardId+CNP+address);

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
    public void removeByNameInfo(String name) {

    }

    @Override
    public Optional<Client> findByNameAccount(String name) {
        return Optional.empty();
    }

    @Override
    public boolean create(Client client) {
        return false;
    }

    @Override
    public boolean updateAccount(Client client) {
        return false;
    }

    @Override
    public void removeByNameAccount(String name) {

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
