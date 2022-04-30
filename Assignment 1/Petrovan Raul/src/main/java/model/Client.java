package model;

import java.util.List;

public class Client {
    private Long id;
    private User user;
    private List<Account> accounts;

    private String name;
    private String idNumber;
    private String CNP;
    private String address;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "\nClient{" +
                ", user=" + user.toString() +
                ", accounts=" + accounts.toString() +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", CNP='" + CNP + '\'' +
                ", address='" + address + '\'' +
                "}\n\n";
    }
}
