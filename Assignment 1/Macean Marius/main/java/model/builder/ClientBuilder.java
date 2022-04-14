package model.builder;

import model.Client;

import java.util.Date;

public class ClientBuilder {
    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdCardNumber(Integer idCardNumber) {
        client.setIdCardNumber(idCardNumber);
        return this;
    }

    public ClientBuilder setIdCode(Long idCode) {
        client.setIdCode(idCode);
        return this;
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public Client build() {
        return client;
    }
}
