package model.builder;

import model.Client;
import view.ClientDTO;

public class ClientBuilder {

    private Client client;

    //constructor
    public ClientBuilder() {
        client = new Client();
    }

    //add builder DTO
    public Client builderClientDTO(ClientDTO c) {

        Client client = new ClientBuilder()
                .setName(c.getName())
                .setAddress(c.getAddress())
                .setIdentificationNr(c.getIdentificationNr())
                .setPersonalNumericalCode(c.getPersonalNumericalCode())
                .build();

        return client;
    }


    public Client build() {
        return client;
    }


    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdentificationNr(Integer identificationNr) {
        client.setIdentificationNr(identificationNr);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setPersonalNumericalCode(String personalNumericalCode) {
        client.setPersonalNumericalCode(personalNumericalCode);
        return this;
    }


}