package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Account {
    private Long id;

    private AccountTypes type;
    private float balance;
    private Date creationDate;
    private String accountNumber;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", type=" + type +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }
}
