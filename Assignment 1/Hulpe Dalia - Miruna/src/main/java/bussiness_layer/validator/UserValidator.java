package bussiness_layer.validator;

import bussiness_layer.models.User;
import repository_layer.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {
  private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
  private static final String PNC_VALIDATION_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
  public static final int MIN_PASSWORD_LENGTH = 8;

  private final List<String> errors = new ArrayList<>();
  private final UserRepository userRepository;

  public UserValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void validate(String username, String password, String pnc) {
    errors.clear();
    validateEmailUniqueness(username);
    validateEmail(username);
    validatePasswordLength(password);
    validatePasswordSpecial(password);
    validatePasswordDigit(password);
    validatePnc(pnc);
    validatePncUniqueness(pnc);
  }

  private void validateEmailUniqueness(String email) {
    User user = userRepository.findByUsername(email);
    if (user == null)
    {
      errors.add("Username is already taken");
    }
  }

  private void validateEmail(String email) {
    if (!email.matches(EMAIL_VALIDATION_REGEX)) {
      errors.add("Email is not valid");
    }
  }

  private void validatePasswordLength(String password) {
    if (!(password.length() >= MIN_PASSWORD_LENGTH)) {
      errors.add("Password must be at least 8 characters long");
    }
  }

  private void validatePasswordSpecial(String password) {
    if (!password.matches(".*[!@#$%^&*()_+].*")) {
      errors.add("Password must contain at least one special character");
    }
  }

  private void validatePasswordDigit(String password) {
    if (!password.matches(".*[0-9].*")) {
      errors.add("Password must contain at least one digit");
    }
  }

  private void validatePnc(String pnc) {
    if (!pnc.matches(PNC_VALIDATION_REGEX)) {
      errors.add("Pnc is not valid");
    }
  }

  public List<String> getErrors() {
    return errors;
  }

  public String getFormattedErrors() {
    return String.join("\n", errors);
  }

  private void validatePncUniqueness(String pnc) {
    User user = userRepository.findByPnc(pnc);
    if (user == null)
    {
      errors.add("Pnc is already taken");
    }
  }
}
