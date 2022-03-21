package model.builder;

import model.Account;
import model.Client;

import java.util.List;

public class ClientBuilder {
    private Client client;
    public ClientBuilder(){client=new Client();}
    public ClientBuilder setName(String name)
    {
        client.setName(name);
        return this;
    }
    public ClientBuilder setIdcardnumber(Long idcardnumber)
    {
        client.setIdcardnumber(idcardnumber);
        return this;
    }
    public ClientBuilder setCnp(Long cnp)
    {
        client.setCnp(cnp);
        return this;
    }

    public ClientBuilder setAddress(String address)
    {
        client.setAddress(address);
        return this;
    }
    public Client build()
    {
        return client;
    }
}
