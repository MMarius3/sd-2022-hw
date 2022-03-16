package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Account {

    private Long id;
    private String type;
    private Integer money;
    private String number;
    private Date date;
}
