package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Transaction {
    private int id;
    private LocalDateTime date;
    private float amount;
    private Account firstPartyAccount;
    private Account secondPartyAccount;
}
