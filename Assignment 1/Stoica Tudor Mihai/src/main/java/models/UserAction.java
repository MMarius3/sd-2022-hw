package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAction {
    private long id;
    private User user;
    private String action;
    private String actionDetails;
}
