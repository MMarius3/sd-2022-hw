package service.client;

import model.Client;
import repository.client.ClientRepositoryMySQL;

import java.util.List;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepositoryMySQL clientRepositoryMySQL;

    public ClientServiceMySQL(ClientRepositoryMySQL clientRepositoryMySQL) {
        this.clientRepositoryMySQL = clientRepositoryMySQL;
    }

    @Override
    public List<Client> findAll() {
        return clientRepositoryMySQL.findAll();
    }

    @Override
    public boolean save(Client client) { return clientRepositoryMySQL.save(client); }

    @Override
    public boolean update(Client client) { return clientRepositoryMySQL.update(client); }
}
