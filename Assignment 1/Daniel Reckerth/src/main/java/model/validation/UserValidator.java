package model.validation;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {
  private static final String EMAIL_VALIDATION_REGEX = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
  private static final String CONTAINS_SPECIAL_CHARACTER_REGEX = "[A-Za-z0-9]";
  private static final String CONTAINS_DIGIT_REGEX = "[0-9]";
  private static final int MIN_PASSWORD_LENGTH = 8;

  private final List<String> errors;
  private final User user;

  public UserValidator(User user) {
    this.user = user;
    errors = new ArrayList<>();
  }

  public List<String> getErrors() {
    return errors;
  }

  public boolean validate() {
    validateUsername(user.getUsername());
    validatePassword(user.getPassword());
    return errors.isEmpty();
  }

  private void validateUsername(String username) {
    if(!Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches()) {
      errors.add("Invalid email!");
    }
  }

  private void validatePassword(String password) {
    if(password == null || password.length() < MIN_PASSWORD_LENGTH) {
      errors.add("Password too short!");
    }

    if(!containsSpecialCharacter(password)) {
      errors.add("Password must contain at least one special character!");
    }

    if(!containsDigit(password)) {
      errors.add("Password must contain at least one digit!");
    }
  }

  private boolean containsSpecialCharacter(String s) {
    if (s == null || s.trim().isEmpty()) {
      return false;
    }

    return Pattern.compile(CONTAINS_SPECIAL_CHARACTER_REGEX).matcher(s).find();
  }

  private boolean containsDigit(String s) {
    if(s == null || s.isEmpty()){
      return false;
    }

    return Pattern.compile(CONTAINS_DIGIT_REGEX).matcher(s).find();
  }

  public String getFormattedErrors() {
    return String.join("\n", errors);
  }
}
