package model;

public class Client_Account {
    private Long id;

    private Long clientId;
    private Long accountID;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAccountId() {
        return accountID;
    }

    public void setAccountId(Long accountID) {
        this.accountID = accountID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
