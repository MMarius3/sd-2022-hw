package Model;

import java.sql.Date;

public class Account {

    private Long accnr;
    private String type;
    private int amount;
    private Date dateOfCreation;

    public Long getAccnr() {
        return accnr;
    }

    public void setAccnr(Long accnr) {
        this.accnr = accnr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
