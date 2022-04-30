package com.assignment2.book_store.data.map;

import com.assignment2.book_store.data.dto.user.mutations.UserCreateRequestDto;
import com.assignment2.book_store.data.dto.user.mutations.UserPatchRequestDto;
import com.assignment2.book_store.data.dto.user.mutations.UserResponseDto;
import com.assignment2.book_store.data.dto.user.searchFilters.UserSearchFilter;
import com.assignment2.book_store.data.entity.jpa.User;
import org.mapstruct.Mapper;

import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto entityToResponseDto(User user);

    User createSearchFilter(UserSearchFilter userSearchFilter);

    User registerUser(UserCreateRequestDto signupRequest);

    default User patchTheUser(User original, UserPatchRequestDto patch) {
        ofNullable(patch.getFirstName()).ifPresent(original::setFirstName);
        ofNullable(patch.getLastName()).ifPresent(original::setLastName);
        ofNullable(patch.getUsername()).ifPresent(original::setUsername);
        ofNullable(patch.getEmail()).ifPresent(original::setEmail);
        ofNullable(patch.getPassword()).ifPresent(original::setPasswordHash);
        ofNullable(patch.getUserRole()).ifPresent(original::setRole);
        ofNullable(patch.getUserStatus()).ifPresent(original::setAccountStatus);
        ofNullable(patch.getAccountBalance()).ifPresent(original::setAccountBalance);
        return original;
    }

}
