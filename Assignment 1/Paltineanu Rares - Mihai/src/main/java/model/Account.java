package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Builder
@Getter
@Setter
public class Account {
    private Long id;
    private Long client_id;
    private String type;
    private Integer money;
    private String number;
    private Date date;
}
