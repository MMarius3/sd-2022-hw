package model.builder;

import model.Client;

import java.util.Date;

public class ClientBuilder {
    private Client client;
    public ClientBuilder(){
        client = new Client();
    }

    public ClientBuilder setID(Long id)
    {
        client.setId(id);
        return this;
    }
    public ClientBuilder setName(String name)
    {
        client.setName(name);
        return this;
    }
    public ClientBuilder setCardID(Long cardID)
    {
        client.setCardID(cardID);
        return this;
    }
    public ClientBuilder setCNP(String CNP)
    {
        client.setCNP(CNP);
        return this;
    }
    public ClientBuilder setAddress(String address)
    {
        client.setAddress(address);
        return this;
    }
    public ClientBuilder setBalance(int balance)
    {
        client.setBalance(balance);
        return this;
    }
    public ClientBuilder setDateOfCreation(Date date)
    {
        client.setDateOfCreation(date);
        return this;
    }
}
