package service.client;

import model.Client;

import java.util.List;

public interface ClientService {

    boolean addClient(String name, String idCardNr, String PNC, String address, String email);

    List<Client> getAllClients();

    Client findByPNC(Long PNC);

    boolean updateClient(Client client);
}
