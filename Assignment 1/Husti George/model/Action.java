package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Action {
    private int id;
    private User user;
    private String name;
    private LocalDateTime date;
}
