package model.builder;

import model.Client;

public class ClientBuilder {
    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setID(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdCardNo(String idCardNo) {
        client.setIdCardNo(idCardNo);
        return this;
    }

    public ClientBuilder setCNP(String cnp) {
        client.setCNP(cnp);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public Client build() {
        return client;
    }
}
