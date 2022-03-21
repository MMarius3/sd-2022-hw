package services.client;

import controller.Response;
import model.Client;
import repository.Client.ClientRepository;

import static java.lang.String.format;

import java.sql.SQLException;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() throws SQLException {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Client with id %d not found", id)));
    }

    @Override
    public boolean save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public boolean update(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public boolean delete(Client client) {
        return clientRepository.delete(client);
    }

    @Override
    public Response<Boolean> existsByCardNr(String cardNr) {
        return clientRepository.existsByField("cardNumber", cardNr);
    }

    @Override
    public Response<Boolean> existsByPnc(String pnc) {
        return clientRepository.existsByField("pnc", pnc);
    }
}
