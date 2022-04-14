package model.builder;

import model.Client;

public class ClientBuilder {
    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setId(long id){
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdCardNumber(int idCardNumber) {
        client.setIdCardNumber(idCardNumber);
        return this;
    }

    public ClientBuilder setPersNumCode(int persNumCode){
        client.setPersNumCode(persNumCode);
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
