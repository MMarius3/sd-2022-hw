package bussiness_layer.models;

import java.sql.Date;

public class Account {
    private Long id;
    private String type;
    private Float amount_of_money;
    private Date date_of_creation;
    private Long user_id;

    public Account() {
    }

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

    public Float getAmount_of_money() {
        return amount_of_money;
    }

    public void setAmount_of_money(Float amount_of_money) {
        this.amount_of_money = amount_of_money;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Date date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
