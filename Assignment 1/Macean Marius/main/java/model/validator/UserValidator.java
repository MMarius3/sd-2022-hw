package model.validator;

import controller.Response;
import model.User;
import repository.user.UserRepository;
import service.user.AuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int MIN_PASSWORD_LENGTH = 8;

    private final List<String> errors = new ArrayList<>();
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public UserValidator(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public void validate(String username, String password) {
        errors.clear();
        validateEmailUniqueness(username);
        validateEmail(username);
        validatePasswordLength(password);
        validatePasswordSpecial(password);
        validatePasswordDigit(password);
    }

    private void validateEmailUniqueness(String email) {
        final Response<Boolean> response = userRepository.existsByUsername(email);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Email is already taken");
            }
        }
    }

    public void validateUpdate(String id, String name) {
        validateEmail(name);
        validateIDOnlyDigits(id);
        validateIDExistence(id);
        validateIDIsEmployee(id);
    }

    public void validateID(String id) {
        validateIDOnlyDigits(id);
        validateIDExistence(id);
        validateIDIsEmployee(id);
    }

    private void validateIDIsEmployee(String id) {
        User user = userRepository.findByExistentId(Long.getLong(id));
        if (!authenticationService.isEmployee(user.getUsername(), user.getPassword())) {
            errors.add("This ID belongs to an administrator. ID must belong to a valid employee");
        }
    }

    private void validateIDOnlyDigits(String id) {
        if (!id.matches("[0-9]+")) {
            errors.add("ID must only contain digits");
        }
    }

    private void validateIDExistence(String id) {
        final Optional<User> user = userRepository.findById(Long.getLong(id));
        if (!user.isPresent()) {
            errors.add("There is no employee having this ID");
        }
    }

    public void validateEmail(String email) {
        if (!email.matches(EMAIL_VALIDATION_REGEX)) {
            errors.add("Username must be your email and it is not valid");
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