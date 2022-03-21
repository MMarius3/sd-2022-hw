package model;

import java.sql.Date;

public class ClientAccount {

    private Integer identificationNumber;
    private String type;
    private Double amount;
    private Date creationDate;
    private Client owner;

    public ClientAccount() {
    }

    public ClientAccount(Client owner, Integer identificationNumber, String type, Double amount, Date creationDate) {
        this.identificationNumber = identificationNumber;
        this.type = type;
        this.amount = amount;
        this.creationDate = creationDate;
        this.owner = owner;
    }

    public Integer getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(Integer identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Account " + identificationNumber +
                ", Owner =  " + owner +
                ", " + type;
    }
}
