package model.builder;

import model.Client;
import model.User;

public class ClientBuilder {

    private Client client;

    public ClientBuilder(){
        client = new Client();
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdCardNr(Long idCardNr){
        client.setIdCardNr(idCardNr);
        return this;
    }

    public ClientBuilder setPNC(Long PNC){
        client.setPNC(PNC);
        return this;
    }

    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setEmail(String email){
        client.setEmail(email);
        return this;
    }

    public Client build() {
        return client;
    }

}
