package repositories.dtos;

import lombok.Getter;
import lombok.Setter;
import models.User;

@Getter
@Setter
public class UserActionDTO {
    private long id;
    private User user;
    private String action;
    private String actionDetails;
}
