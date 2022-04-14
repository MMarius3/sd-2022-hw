package model;

public class Client {

    private int id;
    private String name;
    private Long idCardNr;
    private Long PNC;
    private String address;
    private String email;

    public Long getPNC() {
        return PNC;
    }

    public void setPNC(Long PNC) {
        this.PNC = PNC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdCardNr() {
        return idCardNr;
    }

    public void setIdCardNr(Long idCardNr) {
        this.idCardNr = idCardNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
