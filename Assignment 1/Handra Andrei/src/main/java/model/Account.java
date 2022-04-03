package model;

import java.util.Date;

public class Account {

    private Long identificationNumber;
    private String cardNumber;
    private String type;
    private Float sumOfMoney;
    private Date creationDate;

    public Long getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(Long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getSumOfMoney() {
        return sumOfMoney;
    }

    public void setSumOfMoney(Float sumOfMoney) {
        this.sumOfMoney = sumOfMoney;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
