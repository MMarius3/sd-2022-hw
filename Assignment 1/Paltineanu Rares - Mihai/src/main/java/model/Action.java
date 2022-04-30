package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Builder
@Getter
@Setter
public class Action {

    private Long id;
    private Long user_id;
    private String action;
    private Date date;
}
