package repositories.user_action;

import repositories.dtos.UserActionDTO;

public class UserActionMySQL implements UserAction<UserActionDTO>{
    @Override
    public void deleteByID() {

    }

    @Override
    public long create(UserActionDTO object) {
        return 0;
    }

    @Override
    public UserActionDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(UserActionDTO object) {

    }

    @Override
    public UserActionDTO getByUserID(long userID) {
        return null;
    }

    @Override
    public UserActionDTO getByAction(String action) {
        return null;
    }
}
