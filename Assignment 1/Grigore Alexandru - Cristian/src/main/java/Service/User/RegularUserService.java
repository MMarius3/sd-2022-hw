package Service.User;

import Model.Client;
import Model.ClientAccount;

import java.util.List;

public interface RegularUserService {

    List<Client> findClients();

    List<ClientAccount> findClientAccounts();

    void addClient(Client client);

    void addClientAccount(ClientAccount clientAccount);

    void editClient(Client toBeEdited);

    void editClientAccount(ClientAccount toBeEdited);

    void deleteClientAccount(ClientAccount toBeDeleted);

    void TransferMoney(ClientAccount send, ClientAccount receive, int amount);

    void processBills(ClientAccount toPay, int amount);


}
