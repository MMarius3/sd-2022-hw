package model.validator;

import controller.Response;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MIN_USERNAME_LENGTH = 4;

    private final List<String> errors = new ArrayList<>();
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(String username, String password) {
        errors.clear();
        validateUsernameLength(username);
        validateUsernameUniqueness(username);
        validatePasswordLength(password);
        validatePasswordSpecial(password);
        validatePasswordDigit(password);
    }

    public void validateUsernameLength(String username) {
        if (!(username.length() >= MIN_USERNAME_LENGTH)) {
            errors.add("Username must be at least " + MIN_USERNAME_LENGTH + " characters long");
        }
    }

    public void validateUsernameUniqueness(String username) {
        final Response<Boolean> response = userRepository.existsByUsername(username);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Username is already taken");
            }
        }
    }

    private void validatePasswordLength(String password) {
        if (!(password.length() >= MIN_PASSWORD_LENGTH)) {
            errors.add("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
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

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
