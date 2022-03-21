package service.client;

import controller.Response;
import model.Client;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client with id %d not found.".formatted(id)));
    }

    @Override
    public boolean save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client findByCardNumber(Long cardNumber){
        return clientRepository.findByCardNumber(cardNumber);
    }

    @Override
    public boolean update(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }

    @Override
    public Response<Boolean> existsByCNP(Long personalNumericalCode) {
        return clientRepository.existsByCNP(personalNumericalCode);
    }

    @Override
    public Response<Boolean> existsByName(String name) {
        return clientRepository.existsByName(name);
    }
}
