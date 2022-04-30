package service.login;

import model.User;
import service.login.exception.InvalidUsernameOrPasswordException;

import java.util.Optional;

public class SignIn extends Login {
    @Override
    public void login() throws InvalidUsernameOrPasswordException {
        Optional<User> user = this.userRepository.getUserByUsernameAndPassword(this.username, this.password);
        if(user.isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }
    }
}
