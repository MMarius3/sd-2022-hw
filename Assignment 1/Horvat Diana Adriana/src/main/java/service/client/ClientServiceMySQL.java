package service.client;

import model.Client;
import model.Role;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import respository.client.ClientRepository;

import java.util.Collections;
import java.util.List;

public class ClientServiceMySQL implements ClientService{

    private final ClientRepository clientRepository;
    public ClientServiceMySQL(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean addClient(String name, String idCardNrStr, String PNCStr, String address, String email){
        Long idCardNr = Long.parseLong(idCardNrStr);
        Long PNC = Long.parseLong(PNCStr);

        Client client = new ClientBuilder()
                .setName(name)
                .setIdCardNr(idCardNr)
                .setPNC(PNC)
                .setAddress(address)
                .setEmail(email)
                .build();

        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public Client findByPNC(Long PNC){
        return clientRepository.findByPNC(PNC);
    }

    @Override
    public boolean updateClient(Client client){
        return clientRepository.updateClient(client);
    }
}
