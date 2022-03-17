package model;

import java.util.List;

public class Client {
    private String name;
    private String idNumber;
    private Integer personalNumericalCode;
    private String address;
    //private Integer phoneNumber;
    private List<Account> accounts;


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

    public Integer getPersonalNumericalCode() {
        return personalNumericalCode;
    }

    public void setPersonalNumericalCode(Integer personalNumericalCode) {
        this.personalNumericalCode = personalNumericalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
/*
    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
*/
    public List<Account> getAccount() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
