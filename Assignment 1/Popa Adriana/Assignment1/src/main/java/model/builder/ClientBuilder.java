package model.builder;

import model.Client;

public class ClientBuilder {
    private final Client client;

    public ClientBuilder(){
        client = new Client();
    }

    public ClientBuilder setId(Integer id){
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdentityCardNumber(String identityCardNumber){
        client.setIdentityCardNumber(identityCardNumber);
        return this;
    }

    public ClientBuilder setPersonalNumericCode(String personalNumericCode){
        client.setPersonalNumericalCode(personalNumericCode);
        return this;
    }

    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }

    public Client build(){
        return client;
    }
}
