package model.validation;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final int MIN_PASSWORD_LENGTH = 10;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final User user;

    public UserValidator(User user) {
        this.user = user;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        return errors.isEmpty();
    }

    private void validateUsername(String username) {
        if (!Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches()) {
            errors.add("email is not good, please try again!");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password is too short!");
        }
        if (containsSpecialCharacter(password)!= true) {
            errors.add("Password must contain at least one special character!");
        }
        if (containsDigit(password)!= true) {
            errors.add("Password must contain at least one number!");
        }
    }

    private boolean containsSpecialCharacter(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(password);
        return m.find();
    }

    private boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }
}
