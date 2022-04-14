package repository.client;

import controller.Response;
import model.Account;
import model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    boolean addClient(Client client);

    void updateClient(Client client);

    List<Account> getAccountsForClient();

    Client findClientById(int id);

    Response<Boolean> existsByPNC(String pnc);

}
