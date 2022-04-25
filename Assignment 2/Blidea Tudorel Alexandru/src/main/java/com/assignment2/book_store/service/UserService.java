package com.assignment2.book_store.service;

import com.assignment2.book_store.data.dto.user.mutations.UserCreateRequestDto;
import com.assignment2.book_store.data.dto.user.mutations.UserPatchRequestDto;
import com.assignment2.book_store.data.dto.user.mutations.UserResponseDto;
import com.assignment2.book_store.data.dto.user.searchFilters.UserSearchFilter;
import com.assignment2.book_store.data.entity.jpa.User;
import com.assignment2.book_store.data.map.UserMapper;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final ExampleMatcher DEFAULT_EXAMPLE_MATCHER = ExampleMatcher.matchingAny()
            .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("accountStatus", ExampleMatcher.GenericPropertyMatchers.exact());
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Page<UserResponseDto> getUsers(UserSearchFilter searchFilter, Pageable pageable) {
        User userSearchFilter = userMapper.createSearchFilter(searchFilter);

        Example<User> example = Example.of(userSearchFilter, DEFAULT_EXAMPLE_MATCHER);

        return userRepository.findAll(example, pageable)
                .map(userMapper::entityToResponseDto);
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::entityToResponseDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, String.format("Searched user with id = %d doesn't exist", id)));
    }

    public UserResponseDto createUser(UserCreateRequestDto userCreateRequest) {
        if (userRepository.existsByUsername(userCreateRequest.getUsername())) {
            throw ErrorFactory.getError(HttpStatus.CONFLICT, "Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw ErrorFactory.getError(HttpStatus.CONFLICT, "Error: Email is already in use!");
        }
        return userMapper.entityToResponseDto(userRepository.save(userMapper.registerUser(userCreateRequest)));
    }

    public UserResponseDto updateUser(UserPatchRequestDto patch) {
        encodePassword(patch);
        verifyEligibilityToUpdate(patch);
        User originalUser = userRepository.getById(patch.getId());
        return userMapper.entityToResponseDto(userMapper.patchTheUser(originalUser, patch));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void encodePassword(UserPatchRequestDto patch) {
        ofNullable(patch.getPassword()).map(passwordEncoder::encode).ifPresent(patch::setPassword);
    }

    private void verifyEligibilityToUpdate(UserPatchRequestDto patch) {
        if (patch.getUsername() != null && userRepository.existsByUsername(patch.getUsername())) {
            throw ErrorFactory.getError(HttpStatus.CONFLICT, "Error: Username is already taken!");
        }
        if (patch.getEmail() != null && userRepository.existsByEmail(patch.getEmail())) {
            throw ErrorFactory.getError(HttpStatus.CONFLICT, "Error: Email is already in use!");
        }
    }

}
