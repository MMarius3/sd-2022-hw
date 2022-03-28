package service.user;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceMySQLTest {

    private static EmployeeService employeeService;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static ClientValidator clientValidator;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository = new AccountRepositoryMySQL(connection);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        clientValidator = new ClientValidator(clientRepository);
        CurrentSession session = new CurrentSession();

        employeeService = new EmployeeServiceMySQL(clientRepository,accountRepository,rightsRolesRepository,session);

    }

    @BeforeEach
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    void addNewClient() {
        String name = "Andrei";
        String cnp = "1870818340915";
        String idCardNumber = "ZH493483";
        String address = "acasa";

        Client client = new ClientBuilder()
                .setName(name)
                .setCNP(cnp)
                .setCardNumber(idCardNumber)
                .setAddress(address)
                .build();

        clientValidator.validate(name,cnp,idCardNumber);
        final List<String> errors = clientValidator.getErrors();
        assertEquals(0,errors.size());
        assertTrue(clientRepository.save(client));
    }

    @Test
    void addNewBadClient() {
        String name = "Andrei";
        String cnp = "1870818";
        String idCardNumber = "ZH49";
        String address = "acasa";

        Client client = new ClientBuilder()
                .setName(name)
                .setCNP(cnp)
                .setCardNumber(idCardNumber)
                .setAddress(address)
                .build();

        clientValidator.validate(name,cnp,idCardNumber);
        final List<String> errors = clientValidator.getErrors();
        assertEquals(2,errors.size());

    }


}