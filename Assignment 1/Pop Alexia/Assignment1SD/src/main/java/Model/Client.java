package Model;

public class Client {

    private Long id ;
    private String name;
    private Long cardnr;
    private Long pnc;
    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardnr() {
        return cardnr;
    }

    public void setCardnr(Long cardnr) {
        this.cardnr = cardnr;
    }

    public Long getPnc() {
        return pnc;
    }

    public void setPnc(Long pnc) {
        this.pnc = pnc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
