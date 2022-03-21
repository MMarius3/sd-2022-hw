package model;

public class Client {
    private Integer id;
    private String name;
    private String idNumber;
    private String cnp;
    private String address;

    public Client() {
    }

    public Client(Integer id, String name, String idNumber, String cnp, String address) {
        this.id = id;
        this.name = name;
        this.idNumber = idNumber;
        this.cnp = cnp;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name +
                ", CNP = " + cnp;
    }
}
