package services.user;

import launcher.ComponentFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.user.UserRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationServiceMySQLTest {

    public static final String TEST_USERNAME = "test@username.com";
    public static final String TEST_PASSWORD = "TestPassword1@";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userRepository = componentFactory.getUserRepository();
        authenticationService = componentFactory.getAuthenticationService();
    }

    @BeforeEach
    void cleanUp(){
        userRepository.removeAll();
    }

    @Test
    void register() {
        assertTrue(
                authenticationService.register(TEST_USERNAME, TEST_PASSWORD).getResult()
        );
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }
}