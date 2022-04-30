package service.client;

import model.Client;
import repository.client.ClientRepositoryMySQL;

import java.util.List;

public class ClientServiceDisplayMySQL implements ClientServiceDisplay {

    private final ClientRepositoryMySQL clientRepositoryMySQL;

    public ClientServiceDisplayMySQL(ClientRepositoryMySQL clientRepositoryMySQL) {
        this.clientRepositoryMySQL = clientRepositoryMySQL;
    }

    @Override
    public List<Client> findAll() {
        return clientRepositoryMySQL.findAll();
    }
}
