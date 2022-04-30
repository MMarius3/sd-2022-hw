package repository.user;

import model.Account;
import model.AccountTypes;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import helpers.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserRepositoryPostgres implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryPostgres(Connection connection,
                                  RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from \"" + USER + "\" where username='" + username + "' and" +
                            " password='" + password + "'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(
                                rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
            }
            return findByUsernameAndPasswordNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO \"user\" (username, password) values (?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

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
            String sql = "DELETE from \"user\" where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> findAllClients() {
        Statement statement;
        List<Client> clients = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String fetchClientsSql =
                    "select c.id as clientId, u.id as userId, c.*, u.* from client c join " +
                            "\"user\" u on u.id" +
                            " = c.user_id";
            ResultSet clientResultSet = statement.executeQuery(fetchClientsSql);

            while (clientResultSet.next()) {
                for (int i = 1; i <= clientResultSet.getMetaData().getColumnCount(); i++) {
                    System.out.println(clientResultSet.getMetaData().getColumnName(i));
                }
                System.out.println(clientResultSet.getMetaData().getColumnCount());
                User user = new UserBuilder().setId(clientResultSet.getLong("userId"))
                        .setUsername(clientResultSet.getString("username"))
                        .setId(clientResultSet.getLong("user_id"))
                        .setRoles(rightsRolesRepository.findRolesForUser(
                                clientResultSet.getLong("userId")))
                        .build();
                Client client = new ClientBuilder().setUser(user)
                        .setId(clientResultSet.getLong("clientId"))
                        .setName(clientResultSet.getString("name"))
                        .setIdNumber(clientResultSet.getString("idNumber"))
                        .setCNP(clientResultSet.getString("cnp"))
                        .setAddress(clientResultSet.getString("address"))
                        .setAccounts(findAccountsForClient(clientResultSet.getLong("clientId")))
                        .build();
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Account> findAccountsForClient(Long clientId) {
        Statement statement;
        List<Account> accounts = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String fetchAccountsSql = "select * from account where user_id = " + clientId + ";";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountsSql);
            while (accountResultSet.next()) {
                AccountTypes type = null;
                try {
                    type = AccountTypes.valueOf(accountResultSet.getString("type"));
                } catch (Exception e) {
                    System.out.println("Invalid account type");
                }
                Account account = new AccountBuilder().setId(accountResultSet.getLong("id"))
                        .setBalance(accountResultSet.getInt("balance"))
                        .setType(type)
                        .setCreationDate(accountResultSet.getDate("creation_date"))
                        .setAccountNumber(accountResultSet.getString("account_number"))
                        .build();
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Notification<List<User>> findUsersWithoutAccount() {
        Notification<List<User>> findUsersWithoutAccountNotification = new Notification<>();
        List<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "select * from \"user\" where id not in (select user_id from client)";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            while (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(
                                rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                userList.add(user);
            }
            findUsersWithoutAccountNotification.setResult(userList);
            return findUsersWithoutAccountNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            findUsersWithoutAccountNotification.addError("Something is wrong with the Database");
        }
        return findUsersWithoutAccountNotification;
    }

    @Override
    public Notification<Client> createClient(Client c) {
        Notification<Client> newClientNotification = new Notification<>();
        try {
            PreparedStatement insertClient = connection
                    .prepareStatement("insert into client (user_id, name, \"idNumber\", cnp, " +
                                    "address) values (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            insertClient.setLong(1, c.getUser().getId());
            insertClient.setString(2, c.getName());
            insertClient.setString(3, c.getIdNumber());
            insertClient.setString(4, c.getCNP());
            insertClient.setString(5, c.getAddress());

            insertClient.executeUpdate();

            ResultSet rs = insertClient.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            c.setId(clientId);
            c.setAccounts(findAccountsForClient(clientId));
            newClientNotification.setResult(c);
            return newClientNotification;
        } catch (SQLException e) {
            newClientNotification.addError("Error with database");
            return newClientNotification;
        }
    }

    @Override
    public Notification<Boolean> updateClient(Client c) {
        Notification<Boolean> updateClientNotification = new Notification<>();
        try {
            PreparedStatement updateClient = connection
                    .prepareStatement("update client set \"name\" = ?, \"idNumber\" = ?, cnp = ?," +
                            "address = ? where id = ?;");
            updateClient.setString(1, c.getName());
            updateClient.setString(2, c.getIdNumber());
            updateClient.setString(3, c.getCNP());
            updateClient.setString(4, c.getAddress());
            updateClient.setLong(5, c.getId());

            updateClient.executeUpdate();

            updateClientNotification.setResult(true);
            return updateClientNotification;
        } catch (SQLException e) {
            System.out.println(e.getMessage() + e.getSQLState());
            updateClientNotification.addError("Error with database");
            return updateClientNotification;
        }
    }

    @Override
    public Notification<Boolean> deleteClientById(Long clientId) {
        Notification<Boolean> deleteClientNotification = new Notification<>();
        try {
            PreparedStatement deleteClient = connection
                    .prepareStatement(
                            "delete from account where user_id in (select \"user\".id from \"user\" join client c on \"user\".id = c" +
                                    "    .user_id where c.id = " + clientId + ");" +
                                    "delete from client where id = " + clientId + ";");
            deleteClient.executeUpdate();

            deleteClientNotification.setResult(true);
            return deleteClientNotification;
        } catch (SQLException e) {
            deleteClientNotification.addError("Error with database");
            return deleteClientNotification;
        }
    }

    @Override
    public User findById(Long userId) {
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql =
                    "Select * from \"" + USER + "\" where id=" + userId + " ;";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(
                                rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Notification<Boolean> deleteUserById(Long id) {
        Notification<Boolean> deleteUserNotification = new Notification<>();
        try {
            PreparedStatement deleteUser = connection
                    .prepareStatement(
                            "delete from user_role where user_id = " + id + ";delete from " +
                                    "\"user\" where id = " + id + ";");
            deleteUser.executeUpdate();

            deleteUserNotification.setResult(true);
            return deleteUserNotification;
        } catch (SQLException e) {
            deleteUserNotification.addError("Error with database");
            System.out.println(e.getMessage());
            return deleteUserNotification;
        }
    }

    @Override
    public Notification<Boolean> addRole(Long id, String role) {
        Notification<Boolean> addRoleNotification = new Notification<>();
        try {
            PreparedStatement addRole = connection
                    .prepareStatement(
                            "insert into user_role (user_id, role_id) VALUES (?, (select r.id " +
                                    "from role r where r.role like ?));");
            addRole.setLong(1, id);
            addRole.setString(2, role);
            addRole.executeUpdate();

            addRoleNotification.setResult(true);
            return addRoleNotification;
        } catch (SQLException e) {
            addRoleNotification.addError("Error with database");
            return addRoleNotification;
        }
    }

    @Override
    public Notification<Boolean> removeRole(Long id, String role) {
        Notification<Boolean> removeRoleNotification = new Notification<>();
        try {
            PreparedStatement removeRole = connection
                    .prepareStatement(
                            "delete from user_role where user_id = ? and role_id = (select r.id " +
                                    "from role r where r.role like ?);");
            removeRole.setLong(1, id);
            removeRole.setString(2, role);
            removeRole.executeUpdate();

            removeRoleNotification.setResult(true);
            return removeRoleNotification;
        } catch (SQLException e) {
            removeRoleNotification.addError("Error with database");
            return removeRoleNotification;
        }
    }


}
