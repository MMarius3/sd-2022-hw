package Service.Client;

import Model.Builder.ClientBuilder;
import Model.Client;
import Repository.Client.ClientRepository;
import javafx.collections.ObservableList;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean addClient(String name, Long cardnr,Long pnc,String address) {
        Client client = new ClientBuilder()
                .setName(name)
                .setCardNr(cardnr)
                .setPnc(pnc)
                .setAddress(address)
                .build();

        return clientRepository.save(client);
    }

    @Override
    public ObservableList<Client> viewClients() {
        return clientRepository.findAll();
    }

    @Override
    public boolean updateClient(Long id , String name,Long pnc,String address) {
        return clientRepository.update(id,name,pnc,address);
    }
}
