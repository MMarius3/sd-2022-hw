package model;

import java.time.LocalDate;

public class Account {

        private Long id;
        private String type;
        private Long balance;
        private LocalDate creation;
        private Long client_id;

//    public Account(Long id,String type, Long balance, Long client_id, LocalDate creation) {
//        this.id = id;
//        this.type = type;
//        this.balance = balance;
//        this.creation = creation;
//        this.client_id = client_id;
//    }

    public Long getId() {
        return id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
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

    public LocalDate getCreation() {
        return creation;
    }

    public void setCreation(LocalDate creation) {
        this.creation = creation;
    }
}
