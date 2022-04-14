package service.login;

import lombok.Setter;
import lombok.experimental.Accessors;
import repository.UserRepository;
import service.login.exception.InvalidUsernameOrPasswordException;

@Setter
@Accessors(chain = true)
public abstract class Login {

    protected String username;
    protected String password;
    protected UserRepository userRepository;

    public boolean validateSize() {

        if (username.length() < 5 || password.length() < 5) {
            return false;
        }

        return true;
    }


    public abstract void login() throws InvalidUsernameOrPasswordException;
}
