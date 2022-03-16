package repositories.user;

import repositories.dtos.UserDTO;

public class UserRepositoryMySQL implements UserRepository<UserDTO> {
    @Override
    public void deleteByID() {

    }

    @Override
    public long create(UserDTO object) {
        return 0;
    }

    @Override
    public UserDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(UserDTO object) {

    }
}
