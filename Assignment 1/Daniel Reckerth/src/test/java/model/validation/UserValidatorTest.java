package model.validation;

import common.TestEntityGenerator;
import model.User;
import model.builder.UserBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorTest {

  private final String[] invalidUsernames = {"@gmail.com", ".email@gmail.com", "email@gmail..com", "u`email@gmail.com", ""};
  private final String[] invalidPasswords = {"", "a", "a1", "abcdefd1fd", "abcdefAfd",};
  private final String[] validUsernames = {"john.doe@gmail.com", "john_doe@gmail.com", "johndoe@student.utcluj.ro"};
  private final String[] validPassword = {"Password1", "paSS12345", "Password_123"};

  @Test
  void validate_invalid() {
    List<User> invalidUsers = new ArrayList<>();
    for(int i = 0; i < invalidUsernames.length; i++) {
      User invalidUser = new UserBuilder().setUsername(invalidUsernames[i]).setPassword(invalidPasswords[i]).build();
      invalidUsers.add(invalidUser);
    }

    for(User user : invalidUsers) {
      UserValidator userValidator = new UserValidator(user);
      assertFalse(userValidator.validate());
      assertFalse(userValidator.getErrors().isEmpty());
    }
  }

  @Test
  void validate_valid() {
    List<User> validUsers = new ArrayList<>();
    for(int i = 0; i < validUsernames.length; i++) {
      User validUser = new UserBuilder().setUsername(validUsernames[i]).setPassword(validPassword[i]).build();
      validUsers.add(validUser);
    }

    for(User user : validUsers) {
      UserValidator userValidator = new UserValidator(user);
      assertTrue(userValidator.validate());
      assertTrue(userValidator.getErrors().isEmpty());
    }
  }


}