package repository.user;

import dtos.UserDTO;
import repository.AbstractRepository;

public interface UserRepository<T> extends AbstractRepository<T> {

    UserDTO getByName(String name);
}
