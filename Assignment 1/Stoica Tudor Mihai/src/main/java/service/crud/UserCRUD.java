package service.crud;

import dtos.AccountDTO;
import dtos.ClientDTO;
import dtos.UserDTO;
import model.Client;
import model.User;
import org.modelmapper.ModelMapper;
import repository.AbstractRepository;
import repository.account.AccountRepository;
import repository.user.UserRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class UserCRUD implements CrudActions<User, UserDTO> {

    private ModelMapper userMapper;
    private UserRepository<UserDTO> userRepository;

    @Override
    public CrudActions<User, UserDTO> setRepository(AbstractRepository<UserDTO> repository) {
        this.userRepository = (UserRepository<UserDTO>) repository;
        return this;
    }

    @Override
    public CrudActions<User, UserDTO> setMapper(ModelMapper mapper) {
        this.userMapper = mapper;
        return this;
    }

    @Override
    public Optional<User> getByID(long id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<User>> getAll() {
        return null;
    }

    @Override
    public void deleteByID(long id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<User> update(UserDTO object) {
        return Optional.empty();
    }

    @Override
    public Optional<User> create(UserDTO object) {
        return Optional.empty();
    }

    public Optional<User> getByName(String name) {
        UserDTO userDTO = userRepository.getByName(name);
        return Optional.ofNullable(userMapper.map(userDTO, User.class));
    }
}
