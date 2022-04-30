package com.example.demo.user;

import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final AuthService authService;


    public User findByid(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDTO> allUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.toDto(user);
                    userMapper.populateRolesDTO(user, userDTO);
                    return userDTO;
                })
                .collect(toList());
    }

    public UserDTO create(UserDTO userDTO){

        authService.register(SignupRequest.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(Set.of("EMPLOYEE"))
                .build());

        return userDTO;
    }

    public UserDTO edit(Long id, UserDTO userDTO){
        User user = findByid(id);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return userMapper.toDto(userRepository.save(user));
    }

    public boolean delete(Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
        return user.isPresent();
    }
}
