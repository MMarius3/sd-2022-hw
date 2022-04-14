package service.client;

import model.Client;

import java.util.ArrayList;

public interface ClientService {

    ArrayList<Client> findAll();

    Client findById(Integer id);

    boolean save(Client client);

    boolean delete(Client client);

    boolean add(Client client);

    boolean update(Client client, String idNumber, String address, String cnp, String name);
}
