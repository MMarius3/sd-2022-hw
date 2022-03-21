package model;

import java.util.List;

public class Client {
    private int id;
    private String name;
    private Long idcardnumber;
    private Long cnp;
    private String address;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Long getCnp() {
        return cnp;
    }

    public Long getIdcardnumber() {
        return idcardnumber;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public void setIdcardnumber(Long idcardnumber) {
        this.idcardnumber = idcardnumber;
    }

}