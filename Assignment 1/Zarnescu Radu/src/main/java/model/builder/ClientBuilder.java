package model.builder;

import model.Client;

public class ClientBuilder {
    private final Client client;


    public ClientBuilder() {
        this.client = new Client();
    }

    public ClientBuilder setID(Long id) {
        this.client.setId(id);
        return this;
    }

    public ClientBuilder setCardNumber(String cardNumber) {
        this.client.setCardNumber(cardNumber);
        return this;
    }

    public ClientBuilder setFullName(String fullName) {
        this.client.setFullName(fullName);
        return this;
    }

    public ClientBuilder setPnc(String pnc) {
        this.client.setPnc(pnc);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        this.client.setAddress(address);
        return this;
    }

    public Client build() {
        return this.client;
    }
}
