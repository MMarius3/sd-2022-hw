package model;

public class Client {
    private long id;
    private String name;
    private String address;
    private String pnc;
    private String idCardNumber;

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

    public String getPnc() {
        return pnc;
    }

    public void setPnc(String pnc) {
        this.pnc = pnc;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public long getId() {return id;}

    public String getIdAsString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        return sb.toString();
    }

    public void setId(long id) {this.id = id;}
}
