package com.assignment2.book_store.data.map;

import com.assignment2.book_store.data.dto.user.mutations.UserResponseDto;
import com.assignment2.book_store.data.entity.jpa.User;
import com.assignment2.book_store.data.entity.jpa.enums.UserRole;
import com.assignment2.book_store.data.entity.jpa.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.assignment2.book_store.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserMapperTest {

    private static final Long ID = getRandomLong();
    private static final String FIRST_NAME = getRandomString(60);
    private static final String LAST_NAME = getRandomString(30);
    private static final String EMAIL = getRandomString(60);
    private static final String USERNAME = getRandomString(11);
    private static final String PASSWORD_HASH = getRandomString(100);
    private static final UserRole ROLE = UserRole.ADMIN;
    private static final UserStatus STATUS = UserStatus.ACTIVE;
    private static final Double BALANCE = getRandomDouble(1000, 2000);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMapping() {
        User givenEntity = getUserEntity();
        UserResponseDto expectedUserResponseDto = getUserResponseDto();

        UserResponseDto resultedUserResponseDto = userMapper.entityToResponseDto(givenEntity);

        assertTrue(matching(givenEntity, resultedUserResponseDto));
        assertTrue(matching(expectedUserResponseDto, resultedUserResponseDto));
    }

    private User getUserEntity() {
        return User.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .username(USERNAME)
                .passwordHash(PASSWORD_HASH)
                .role(ROLE)
                .accountStatus(STATUS)
                .accountBalance(BALANCE)
                .build();
    }

    private UserResponseDto getUserResponseDto() {
        return UserResponseDto.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .username(USERNAME)
                .role(ROLE)
                .accountBalance(BALANCE)
                .build();
    }

    private boolean matching(User user, UserResponseDto userResponse) {
        return user.getId().equals(userResponse.getId()) && user.getFirstName().equals(userResponse.getFirstName()) &&
                user.getLastName().equals(userResponse.getLastName()) && user.getEmail().equals(userResponse.getEmail()) &&
                user.getUsername().equals(userResponse.getUsername()) && user.getRole().equals(userResponse.getRole()) &&
                user.getAccountBalance().equals(userResponse.getAccountBalance());
    }

    private boolean matching(UserResponseDto user, UserResponseDto userResponse) {
        return user.getId().equals(userResponse.getId()) && user.getFirstName().equals(userResponse.getFirstName()) &&
                user.getLastName().equals(userResponse.getLastName()) && user.getEmail().equals(userResponse.getEmail()) &&
                user.getUsername().equals(userResponse.getUsername()) && user.getRole().equals(userResponse.getRole()) &&
                user.getAccountBalance().equals(userResponse.getAccountBalance());
    }

}