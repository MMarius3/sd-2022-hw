package model;

import java.util.Date;

public class Account {
    private Long id;
    private String clientId;
    private String type;
    private Long balance;
    private Date created_at;

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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return created_at;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setDate(Date created_at) {
        this.created_at = created_at;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(clientId+ " ").append(type+" ").append(balance+" ");
        return stringBuilder.toString();
    }
}
