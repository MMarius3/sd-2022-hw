package service.client;

import model.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    boolean save(Client client);

    boolean update(Client client);

    boolean addClient(String name, Integer idCardNumber, Long idCode);

    boolean updateClient(Long id, String name, Integer idCardNumber, Long idCode);

    Client viewClient(Long id);

}