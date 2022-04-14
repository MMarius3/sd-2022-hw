package service.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.client.ClientRepository;

import java.util.List;

public class CRUClientMySQL implements CRUClient{
    private final ClientRepository clientRepository;

    public CRUClientMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> viewClients() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<Boolean> addClient(String name, String cnp, String iCNo, String address) {
        Client client = new ClientBuilder()
                .setName(name)
                .setCNP(cnp)
                .setIdCardNo(iCNo)
                .setAddress(address)
                .build();

        ClientValidator clientValidator = new ClientValidator(clientRepository, client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> addClientNotification = new Notification<>();

        if(!clientValid){
            clientValidator.getErrors().forEach(addClientNotification::addError);
            addClientNotification.setResult(Boolean.FALSE);
        } else {
            Long id = clientRepository.save(client);
            if (id != -1L) {
                addClientNotification.setResult(Boolean.TRUE);
            } else {
                addClientNotification.setResult(Boolean.FALSE);
            }
        }
        return addClientNotification;
    }

//    @Override
//    public boolean addClient(Client client) {
//        return clientRepository.save(client) != -1L;
//    }

    @Override
    public boolean updateClient(Long id, Client updatedClient) {
        boolean update = false;
        clientRepository.update(id, updatedClient.getName(), updatedClient.getCNP(), updatedClient.getIdCardNo(), updatedClient.getAddress());

        return update;

    }

    @Override
    public Client searchById(Long id) {
        return clientRepository.findByID(id);
    }
}
