package com.lab4.demo.user;

import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final PasswordEncoder encoder;

    public List<UserDTO> allUsers(){
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(toList());
    }

    public UserDTO create(UserDTO userDTO) {
        SignupRequest signupRequest = SignupRequest.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(Set.of("CUSTOMER"))
                .build();

        authService.register(signupRequest);

        return userDTO;
    }

    public UserDTO edit(Long id, UserDTO userDTO) {
        Optional<User> actUser = userRepository.findById(id);
        User user = actUser.get();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }

}
