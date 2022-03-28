package model.validator;


import model.User;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class LoginValidator {


    private final List<String> errors = new ArrayList<>();
    private final UserRepository userRepository;

    public LoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(String username, String password, User user) {
        errors.clear();
        verifyUserNameAndPasswordEntry(username,password);
        matchPasswordAndUsername(user);

    }

    private void verifyUserNameAndPasswordEntry(String username, String password){
        if(username.isEmpty()){
            errors.add("Email field is empty");
        }
        if(password.isEmpty()){
            errors.add("Password field is empty");
        }
    }

    private void matchPasswordAndUsername(User user){
        if(user == null){
            errors.add("Wrong email or password");
        }
    }




    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
