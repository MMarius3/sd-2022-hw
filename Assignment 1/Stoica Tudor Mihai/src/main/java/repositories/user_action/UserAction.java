package repositories.user_action;

import repositories.AbstractRepository;
import repositories.dtos.UserActionDTO;

public interface UserAction<T> extends AbstractRepository<T> {

    UserActionDTO getByUserID(long userID);
    UserActionDTO getByAction(String action);
}
