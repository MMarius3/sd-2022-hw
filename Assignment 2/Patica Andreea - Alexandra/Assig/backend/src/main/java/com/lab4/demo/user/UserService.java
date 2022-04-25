package com.lab4.demo.user;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.user.dto.UserDetailsImpl;
import com.lab4.demo.user.dto.UserDto;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.model.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public List<UserMinimalDTO> allUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());

    }

    public UserDto findById(Long id){
        return userRepository.findById(id)
                .map(UserDto::toDto)
                .orElseThrow(() -> new RuntimeException(format("User with id %s not found", id)));
    }

    public List<User> allRegularUsers(){
        //return userRepository.findAllByRoleEquals(ERole.EMPLOYEE.toString());

        return userRepository.findUserByRolesIsContaining(roleRepository.findByName(ERole.EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Cannot find role: " + ERole.EMPLOYEE)));
    }

    public UserDto addUser(UserDto userDto){  //TODO class for builder
        return UserDto.toDto(userRepository.save(User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(userDto.getRoles())
                .build()
        ));
    }

    public UserDto edit(Long id, UserDto user){
        UserDto actUserDto = findById(id);
        User actUser = User.builder()
                .id(id)
                .username(actUserDto.getUsername())
                .password(actUserDto.getPassword())
                .roles(actUserDto.getRoles())
                .build();
        actUser.setEmail(user.getEmail());
        actUser.setPassword(user.getPassword());
        return UserDto.toDto(userRepository.save(actUser));
    }

    public boolean delete(Long id){
        boolean message = false;
        try{
            userRepository.deleteById(id);
            message = true;
        }catch(IllegalArgumentException e){
            System.out.println("Item not found to delete");
        }
        return message;
    }
}
