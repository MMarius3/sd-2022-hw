package model;

import java.sql.Date;

public class Account {
    private Long identificationNumber;
    private String type;
    private Double amount;
    private Date dateOfCreation;

    public Long getAccountId() {
        return identificationNumber;
    }

    public void setAccountId(Long identificationNumber) {
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

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
