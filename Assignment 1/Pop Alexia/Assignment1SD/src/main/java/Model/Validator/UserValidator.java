package Model.Validator;

import Controller.Response;
import Repository.User.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int MIN_PASSWORD_LENGTH = 8;

    private final List<String> errors = new ArrayList<>();
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUpdate(String id,String username,String password){
        errors.clear();
        if(id.equals(""))
            errors.add("Input an employee id");
        else if(userRepository.findById(Long.parseLong(id)) == null)
            errors.add("Employee with given id does not exist");
        else if(username.equals("") && password.equals(""))
            errors.add("Input a username and/or password to update");
        else if(username.equals("")) validatePasswordOnly(password);
        else if(password.equals("")) validateEmailOnly(username);
        else validate(username,password);
    }

    public void validateDelete(String username){
        errors.clear();
        if(username.equals(""))
            errors.add("Input a username to delete");
        else {
            validateEmail(username);
            if(!userRepository.existsByUsername(username).getData())
                errors.add("User does not exist");
        }
    }

    public void validateLogin(String username,String password){
        errors.clear();
        if(username.equals(""))
            errors.add("Enter a username");
        if(password.equals(""))
            errors.add("Enter a password");
        if(errors.isEmpty()){
            if(!userRepository.existsByUsername(username).getData())
                errors.add("No active account found");
        }
    }

    public void validate(String username, String password) {
        errors.clear();
        if(username.equals("") || password.equals(""))
            errors.add("Input a username and a password");
        else{
            validateEmailUniqueness(username);
            validateEmail(username);
            validatePasswordLength(password);
            validatePasswordSpecial(password);
            validatePasswordDigit(password);
        }
    }

    public void validateEmailOnly(String username){
        errors.clear();
        validateEmailUniqueness(username);
        validateEmail(username);
    }

    public void validatePasswordOnly(String password){
        errors.clear();
        validatePasswordDigit(password);
        validatePasswordSpecial(password);
        validatePasswordLength(password);
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

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
