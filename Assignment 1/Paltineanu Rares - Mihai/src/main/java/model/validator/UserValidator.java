package model.validator;

import controller.Response;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static final int MIN_PASSWORD_LENGTH = 8;

    private final List<String> errors = new ArrayList<>();
    private final UserRepository employeeRepository;

    public UserValidator(UserRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void validate(String username, String password) {
        errors.clear();
        validateUsernameUniqueness(username);
        validatePasswordLength(password);
        validatePasswordSpecial(password);
        validatePasswordDigit(password);
    }

    public void validateUsernameUniqueness(String username) {
        final Response<Boolean> response = employeeRepository.existsByUsername(username);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Email is already taken");
            }
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

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
