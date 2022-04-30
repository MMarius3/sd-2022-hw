package repositories.client;

import model.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id);

    void updateName(String newName, Long id);

    void updatePersonalNumericalCode(Long newPersonalNumericalCode, Long id);

    void updateAddress(String newAddress, Long id);

    boolean save(Client client);

    void removeAll();

    void updateClientInformation(String newName, String newAddress, Long personalNumericalCode,Long id);
}
