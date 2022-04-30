package model;

//import java.sql.Date;
import java.util.*;

public class Account {
    private Long id;
    private Long balance;
    private String type;
    private Date dateOfCreation;

    private Long client_id;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id=id;
    }

    public Long getBalance(){
        return this.balance;
    }

    public void setBalance(Long balance)
    {
        this.balance=balance;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }


    public String getType() {return this.type;}

    public void setType(String type) {this.type=type;}


    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public void receiveMoney(Long sum){
        this.balance+=sum;
    }

    public void transferMoney(Long sum){
        this.balance-=sum;
    }


}
