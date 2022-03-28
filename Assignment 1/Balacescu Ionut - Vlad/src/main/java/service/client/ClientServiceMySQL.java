package service.client;

import model.Account;
import model.Client;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceMySQL implements ClientService{
    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public boolean addClient(Client client){
       return clientRepository.addClient(client);
    }

    @Override
    public void updateClient(Client client) {
        clientRepository.updateClient(client);
    }

    @Override
    public List<Account> getAccountsForClient() {
        return clientRepository.getAccountsForClient();
    }

    @Override
    public Client findClientById(int id) {
        return null;
    }
}
