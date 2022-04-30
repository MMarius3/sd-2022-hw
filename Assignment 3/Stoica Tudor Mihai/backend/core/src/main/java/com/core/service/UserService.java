package com.core.service;

import com.core.dto.user.UserDto;
import com.core.mapper.UserMapper;
import com.infrastructure.repository.UserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@ComponentScan({"mapper"})
@ComponentScan({"repository"})
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void create(UserDto userDto) {
        userRepository.save(userMapper.userDtoToUser(userDto));
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public void update(UserDto userDto) {
        userRepository.save(userMapper.userDtoToUser(userDto));
    }

    public Optional<UserDto> getById(int userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::userToUserDto);
    }

    public Optional<UserDto> getByName(String username) {
        return userRepository
                .findByName(username)
                .map(userMapper::userToUserDto);
    }
}
