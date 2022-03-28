package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.*;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
    private static Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();

    @BeforeAll
    public static void setupClass() {
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
    }

    @BeforeEach
    void cleanUp() {
        accountRepository.removeAll();
    }

    @AfterAll
    public static void clean(){
        clientRepository.removeAll();
        accountRepository.removeAll();
    }

    @AfterEach
    public void cleanAfter(){
        clientRepository.removeAll();
        accountRepository.removeAll();
    }

    @Test
    void findAll() {
        List<Account> accounts;
        accounts = accountRepository.findAll();
        assertEquals(0,accounts.size());
    }

    @Test
    void findAccountByIdentificationNumber() {
        Client client1 = new ClientBuilder()
                .setName("Andrei")
                .setCardNumber("1234567887654321")
                .setCNP("40000506005605")
                .setAddress("Strada P.")
                .build();
        clientRepository.save(client1);
    }


}