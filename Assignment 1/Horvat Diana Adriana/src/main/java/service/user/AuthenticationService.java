package service.user;

import model.Sentinel;
import model.User;

public interface AuthenticationService {

    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);

    String encodePassword(String password);

    Sentinel getSentinel();

    void setSentinel(Sentinel sentinel);
}
