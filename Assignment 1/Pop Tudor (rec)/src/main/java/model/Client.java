package model;

import java.util.List;

public class Client {
    private Long id;
    private String name;
    private Integer IdentificationNr;
    private String address;
    private String personalNumericalCode;
    private List<Account> accounts;

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

    public Integer getIdentificationNr() {
        return IdentificationNr;
    }

    public void setIdentificationNr(Integer identificationNr) {
        IdentificationNr = identificationNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalNumericalCode() {
        return personalNumericalCode;
    }

    public void setPersonalNumericalCode(String personalNumericalCode) {
        this.personalNumericalCode = personalNumericalCode;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
