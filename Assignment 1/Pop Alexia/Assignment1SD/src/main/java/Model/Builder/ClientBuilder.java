package Model.Builder;
import Model.Client;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setCardNr(Long cardNr) {
        client.setCardnr(cardNr);
        return this;
    }
    public ClientBuilder setPnc(Long pnc){
        client.setPnc(pnc);
        return this;
    }
    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }

    public Client build() {
        return client;
    }
}
