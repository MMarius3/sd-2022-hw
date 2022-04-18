package service;

import dto.UserDto;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.Optional;

@Service
@ComponentScan({"mapper"})
@ComponentScan({"repository"})
public class UserService {

//    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
//        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

//    public void create(UserDto userDto) {
//        userRepository.save(userMapper.userDtoToUser(userDto));
//    }
//
//    public void delete(int id) {
//        userRepository.deleteById(id);
//    }
//
//    public void update(UserDto userDto) {
//        userRepository.save(userMapper.userDtoToUser(userDto));
//    }
//
//    public Optional<UserDto> getById(int userId) {
//        return userRepository
//                .findById(userId)
//                .map(userMapper::userToUserDto);
//    }
//
//    public Optional<UserDto> getByName(String username) {
//        return userRepository
//                .findByName(username)
//                .map(userMapper::userToUserDto);
//    }
}
