package repository.user_action;

import repository.AbstractRepository;
import dtos.UserActionDTO;

import java.util.List;

public interface UserActionRepository<T> extends AbstractRepository<T> {

    List<UserActionDTO> getByUserID(long userID);
    UserActionDTO getByAction(String action);
}
