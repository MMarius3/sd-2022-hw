package service.client;

import model.Account;
import model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    boolean addClient(Client client);

    void updateClient(Client client);

    List<Account> getAccountsForClient();

    Client findClientById(int id);
}
