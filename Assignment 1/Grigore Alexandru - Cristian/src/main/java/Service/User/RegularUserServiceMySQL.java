package Service.User;

import Database.JDBConnectionWrapper;
import Model.Client;
import Model.ClientAccount;
import Repository.Client.ClientRepository;
import Repository.ClientAccount.ClientAccountRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static Database.Constants.Schemas.PRODUCTION;

public class RegularUserServiceMySQL implements RegularUserService{

    private final ClientRepository clientRepository;
    private final ClientAccountRepository clientAccountRepository;

    public RegularUserServiceMySQL(ClientRepository clientRepository, ClientAccountRepository clientAccountRepository){
        this.clientRepository = clientRepository;
        this.clientAccountRepository = clientAccountRepository;
    }

    @Override
    public List<Client> findClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public List<ClientAccount> findClientAccounts() {
        return this.clientAccountRepository.findAll();
    }

    @Override
    public void addClient(Client client) {
        this.clientRepository.save(client);
    }

    @Override
    public void addClientAccount(ClientAccount clientAccount) {
        this.clientAccountRepository.save(clientAccount);
    }

    @Override
    public void editClient(Client toBeEdited) {
        Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        try{
            Statement statement = connection.createStatement();
            String update = "UPDATE clients" +
                    " SET name = '"  + toBeEdited.getName() +
                    "', identityCardNumber = "+toBeEdited.getIdentityCardNumber() +
                    ", personalNumericalCode = '"+toBeEdited.getPersonalNumericalCode() +
                    "', address = '"+toBeEdited.getAddress()+
                    "' WHERE id = " + toBeEdited.getId() + ";";
            statement.executeUpdate(update);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void editClientAccount(ClientAccount toBeEdited) {
        Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        try{
            Statement statement = connection.createStatement();
            String update = "UPDATE client_accounts" +
                    " SET  identificationNumber ="  + toBeEdited.getIdentificationNumber() +
                    ", type = '"+toBeEdited.getType() +
                    "', money = '"+toBeEdited.getMoney() +
                    "', creationDate = '"+ new java.sql.Date(toBeEdited.getCreationDate().getTime())+
                    "' WHERE id = " + toBeEdited.getId() + ";";
            statement.executeUpdate(update);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClientAccount(ClientAccount toBeDeleted) {
        Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        try{
            Statement statement = connection.createStatement();
            String delete = "DELETE FROM client_accounts WHERE id = " + toBeDeleted.getId() + ";";
            statement.executeUpdate(delete);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void TransferMoney(ClientAccount send, ClientAccount receive, int amount) {
        send.setMoney(send.getMoney() - amount);
        receive.setMoney(receive.getMoney() + amount);
        editClientAccount(send);
        editClientAccount(receive);
    }

    @Override
    public void processBills(ClientAccount toPay, int amount) {
        toPay.setMoney(toPay.getMoney() - amount);
        editClientAccount(toPay);
    }
}
