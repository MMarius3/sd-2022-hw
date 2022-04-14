package service.login.exception;

public class InvalidUsernameOrPasswordException extends Exception {

    public InvalidUsernameOrPasswordException() {
        super("Invalid username or password");
    }
}
