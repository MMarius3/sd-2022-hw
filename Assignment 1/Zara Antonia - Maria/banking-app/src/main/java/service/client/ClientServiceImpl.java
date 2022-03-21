package service.client;

import model.Client;
import model.ClientAccount;
import model.validator.ClientValidator;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.client_account.ClientAccountRepository;

import java.util.ArrayList;

public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;

    public ClientServiceImpl(ClientRepository clientRepository, ClientValidator clientValidator){
        this.clientRepository = clientRepository;
        this.clientValidator = clientValidator;
    }


    @Override
    public ArrayList<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Integer id) {
        return null;
    }

    @Override
    public boolean save(Client client) {
        return false;
    }

    @Override
    public boolean delete(Client client) {
        return false;
    }

    @Override
    public boolean add(Client client) {
        return clientRepository.add(client);
    }

    @Override
    public boolean update(Client client, String idNumber, String address, String cnp, String name) {
        Client updatedClient = new Client();

        updatedClient.setId(client.getId());

        if(idNumber == null) {
            updatedClient.setIdNumber(client.getIdNumber());
        }
        else{
            updatedClient.setIdNumber(idNumber);
        }

        if(address == null) {
            updatedClient.setAddress(client.getAddress());
        }
        else{
            updatedClient.setAddress(address);
        }

        if(cnp == null) {
            updatedClient.setCnp(client.getCnp());
        }
        else{
            updatedClient.setCnp(cnp);
        }

        if(name == null) {
            updatedClient.setName(client.getName());
        }
        else{
            updatedClient.setName(name);
        }

        clientValidator.validate(name, cnp, idNumber, address);

        return clientRepository.update(updatedClient);
    }
}
