package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserAction {
    private long id;
    private User user;
    private String action;
    private String actionDetails;
}
