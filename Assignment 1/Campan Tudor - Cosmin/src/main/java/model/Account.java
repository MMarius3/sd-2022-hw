package model;

import java.util.Date;

public class Account {
    private int id;
    private Long idnumber;
    private String type;
    private Long money;
    private String dateOfCreation;
    private int clientID;

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getIdnumber() {
        return idnumber;
    }

    public Long getMoney() {
        return money;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setIdnumber(Long idnumber) {
        this.idnumber = idnumber;
    }

}