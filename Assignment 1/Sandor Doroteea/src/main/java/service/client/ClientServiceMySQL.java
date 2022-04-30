package service.client;

import model.Client;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceMySQL implements ClientService{

    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository userRepository) {
        this.clientRepository = userRepository;
    }

    @Override
    public boolean add(String name, String idCardNo, String cnp, String address) {

       Client client = new Client();
        client.setName(name);
        client.setIdCardNumber(idCardNo);
        client.setCnp(cnp);
        client.setAddress(address);

        return clientRepository.save(client);
    }

    @Override
    public boolean delete(Long id) {
        return clientRepository.removeById(id);
    }

    @Override
    public List<Client> view() {
        return clientRepository.findAll();
    }

    @Override
    public boolean update(String name, String idCardNo, String cnp, String address,Long id) {

        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setIdCardNumber(idCardNo);
        client.setCnp(cnp);
        client.setAddress(address);

        return clientRepository.update(client);
    }
}
