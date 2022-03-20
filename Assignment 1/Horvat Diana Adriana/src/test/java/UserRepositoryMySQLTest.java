import database.DBConnectionFactory;
import org.junit.jupiter.api.BeforeAll;
import respository.user.UserRepository;
import respository.user.UserRepositoryMySQL;

import java.sql.Connection;

public class UserRepositoryMySQLTest {

    private static UserRepositoryMySQL userRepositoryMySQL;

//    @BeforeAll
//    public static void setup(){
//        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
//        userRepositoryMySQL = new UserRepositoryMySQL(connection);
//    }
}
