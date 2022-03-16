package models;

import java.util.Date;
import java.util.List;

public class Account {
    private long clientID;
    private long id;
    private String identificationNumber;
    private float sum;
    private Currency currency;
    private Date creationDate;
    private List<Transaction> transactionList;
}
