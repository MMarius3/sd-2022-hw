package model.builder;

import model.Client;

public class ClientBuilder {
    private Client client;

    public ClientBuilder() {
        this.client = new Client();
    }

    public ClientBuilder setId(Long id){
        client.setId(id);
        return this;
    }

    public ClientBuilder setFullName(String fullName){
        client.setFullName(fullName);
        return this;
    }

    public ClientBuilder setIdNumber(String idNumber){
        client.setIdNumber(idNumber);
        return this;
    }

    public ClientBuilder setCNP(String cnp){
        client.setCnp(cnp);
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
