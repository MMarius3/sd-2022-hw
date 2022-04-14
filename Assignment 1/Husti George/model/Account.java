package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Account {
    private int id;
    private Client client;
    private String identificationNumber;
    private float balance;
    private LocalDateTime creationDate;
}
