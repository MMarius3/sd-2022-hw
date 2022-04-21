package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO create(UserDTO userDTO) {
        User actUser = userMapper.fromDto(userDTO);
        actUser.setPassword(passwordEncoder.encode(actUser.getPassword()));
        Role r = roleRepository.findByName(ERole.EMPLOYEE).orElseThrow(() -> new EntityNotFoundException("Role not found: " + ERole.EMPLOYEE));
        actUser.setRoles(Set.of(r));
        return userMapper.toDto(userRepository.save(actUser));
    }
    public UserDTO findById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id)));
    }

    public List<UserDTO> filterUsers(String filter) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return userRepository.findAllByUsernameLikeOrEmailLike("%"+filter+"%","%"+ filter+"%",pageRequest)
                .stream().map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO edit(Long id,UserDTO userDTO) {
        User actUser = userRepository.findById(id).get();
        actUser.setUsername(userDTO.getUsername());
        actUser.setEmail(userDTO.getEmail());
        if(userDTO.getPassword() != null) {
            actUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return userMapper.toDto(userRepository.save(actUser));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
