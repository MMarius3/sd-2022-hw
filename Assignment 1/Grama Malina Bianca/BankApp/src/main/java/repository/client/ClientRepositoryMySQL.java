package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements repository.client.ClientRepository {
    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client findByID(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `id`=\'" + id + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();

            return new ClientBuilder()
                    .setID(id)
                    .setName(clientResultSet.getString("name"))
                    .setIdCardNo(clientResultSet.getString("card_no"))
                    .setCNP(clientResultSet.getString("CNP"))
                    .setAddress(clientResultSet.getString("address"))
                    .build();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Client findByCNP(String cnp) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `CNP`=\'" + cnp + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();

            return new ClientBuilder()
                    .setID((long) clientResultSet.getInt("id"))
                    .setCNP(cnp)
                    .setName(clientResultSet.getString("name"))
                    .setIdCardNo(clientResultSet.getString("card_no"))
                    .setAddress(clientResultSet.getString("address"))
                    .build();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public boolean existsIDCardNo(String idCardNo) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `card_no`=\'" + idCardNo + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            if(clientResultSet.next()) {
                return true;
            }
//            return new ClientBuilder()
//                    .setID((long) clientResultSet.getInt("id"))
//                    .setCNP(clientResultSet.getString("CNP"))
//                    .setName(clientResultSet.getString("name"))
//                    .setIdCardNo(idCardNo)
//                    .setAddress(clientResultSet.getString("address"))
//                    .build();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public boolean existsCNP(String cnp) {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from `" + CLIENT + "` where `CNP`=\'" + cnp + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            if(clientResultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public long save(Client client) {
        try {
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertClientStatement.setString(1, client.getName());
            insertClientStatement.setString(2, client.getIdCardNo());
            insertClientStatement.setString(3, client.getCNP());
            insertClientStatement.setString(4, client.getAddress());
            insertClientStatement.executeUpdate();

            ResultSet rs = insertClientStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);

            return clientId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public boolean update(Long clientId, String name, String cnp, String card_no, String address) {
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE client SET name = ?, card_no = ?, cnp = ?, address = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            updateClientStatement.setString(1, name);
            updateClientStatement.setString(2, card_no);
            updateClientStatement.setString(3, cnp);
            updateClientStatement.setString(4, address);
            updateClientStatement.setLong(5, clientId);
            updateClientStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateName(Client client, String name) {
        String icNum = client.getIdCardNo();
        String cnp = client.getCNP();
        String address = client.getAddress();
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE client SET name = ? WHERE card_no = ? AND CNP = ? AND address = ?", Statement.RETURN_GENERATED_KEYS);
            updateClientStatement.setString(1, name);
            updateClientStatement.setString(2, icNum);
            updateClientStatement.setString(3, cnp);
            updateClientStatement.setString(4, address);
            updateClientStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateICNum(Client client, String icNum) {
        String name = client.getName();
        String cnp = client.getCNP();
        String address = client.getAddress();
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE client SET card_no = ? WHERE name = ? AND CNP = ? AND address = ?", Statement.RETURN_GENERATED_KEYS);
            updateClientStatement.setString(2, name);
            updateClientStatement.setString(1, icNum);
            updateClientStatement.setString(3, cnp);
            updateClientStatement.setString(4, address);
            updateClientStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCNP(Client client, String cnp) {
        String icNum = client.getIdCardNo();
        String name = client.getName();
        String address = client.getAddress();
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE client SET CNP = ? WHERE card_no = ? AND name = ? AND address = ?", Statement.RETURN_GENERATED_KEYS);
            updateClientStatement.setString(3, name);
            updateClientStatement.setString(2, icNum);
            updateClientStatement.setString(1, cnp);
            updateClientStatement.setString(4, address);
            updateClientStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAddress(Client client, String address) {
        String icNum = client.getIdCardNo();
        String cnp = client.getCNP();
        String name = client.getName();
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE client SET address = ? WHERE card_no = ? AND CNP = ? AND name = ?", Statement.RETURN_GENERATED_KEYS);
            updateClientStatement.setString(4, name);
            updateClientStatement.setString(2, icNum);
            updateClientStatement.setString(3, cnp);
            updateClientStatement.setString(1, address);
            updateClientStatement.executeUpdate();
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
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setID(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdCardNo(rs.getString("card_no"))
                .setCNP(rs.getString("cnp"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
