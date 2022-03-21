package model;

import java.time.LocalDate;

public class Account {
    private Long id;
    private String type;
    private Float balance;
    private LocalDate dateOfCreation;
    private Long clientID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long userID) {
        this.clientID = userID;
    }
}
