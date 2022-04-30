package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(user -> {UserListDTO userDTO = userMapper.userListDtoFromUser(user);
                userMapper.populateRoles(user,userDTO);
                return userDTO;})
                .collect(toList());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO create(UserListDTO user) {
        User user1 = userMapper.userFromUserListDto(user);

        Set<String> rolesStr = user.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user1.setRoles(roles);
        user1.setPassword(encoder.encode(user.getPassword()));
        return userMapper.userListDtoFromUser(userRepository.save(user1));
    }

    public UserListDTO update(Long id,UserListDTO user) {
        User actUser = findById(id);
        actUser.setUsername(user.getName());
        actUser.setPassword(encoder.encode(user.getPassword()));
        actUser.setEmail(user.getEmail());

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .name(ERole.EMPLOYEE)
                .build());
        actUser.setRoles(roles);
        return userMapper.userListDtoFromUser(userRepository.save(actUser));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
