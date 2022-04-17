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

  public ClientBuilder setCnp(String cnp) {
    client.setCnp(cnp);
    return this;
  }

  public ClientBuilder setAddress(String address) {
    client.setAddress(address);
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
