package service.client;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import repository.client.ClientRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean register(String id_card_number, String personal_numerical_code, String address,String name) {

        Client client = new ClientBuilder()
                .setIdCardNumber(id_card_number)
                .setPersonalNumericalCode(personal_numerical_code)
                .setAddress(address)
                .setName(name)
                .build();

        return clientRepository.save(client);
    }

}