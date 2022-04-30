package model;

public class Client {

    private long id;
    private String name;
    private int idCardNumber;
    private int persNumCode;
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(int idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public int getPersNumCode() {
        return persNumCode;
    }

    public void setPersNumCode(int persNumCode) {
        this.persNumCode = persNumCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
