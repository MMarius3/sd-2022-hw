package Service;

import Database.DatabaseConnectionFactory;
import Model.Builder.ClientAccountBuilder;
import Model.Builder.ClientBuilder;
import Model.Client;
import Model.ClientAccount;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import Repository.ClientAccount.ClientAccountRepository;
import Repository.ClientAccount.ClientAccountRepositoryMySQL;
import Repository.Security.RightRolesRepositoryMySQL;
import Repository.Security.RightsRolesRepository;
import Repository.User.UserRepositoryMySQL;
import Service.User.AuthenticationServiceMySQL;
import Service.User.RegularUserService;
import Service.User.RegularUserServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RegularUserServiceMySQLTest {

    private static RegularUserService regularUserService;
    private static ClientRepository clientRepository;
    private static ClientAccountRepository clientAccountRepository;

    @BeforeAll
    public static void setUp(){
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        clientAccountRepository = new ClientAccountRepositoryMySQL(connection);
        regularUserService = new RegularUserServiceMySQL(clientRepository,clientAccountRepository);
    }

    @BeforeEach
    public void cleanUp(){
        clientRepository.removeAll();
        clientAccountRepository.removeAll();
    }

    @Test
    public void addClients(){
        Client client = new ClientBuilder()
                .setName("test name")
                .setIdentityCardNumber(123456L)
                .setPersonalNumericalCode("1234567891234")
                .setAddress("walls")
                .build();
        assertTrue(clientRepository.save(client));
    }

    @Test
    public void addClientAccount(){
        Client client = new ClientBuilder()
                .setName("test name")
                .setIdentityCardNumber(123456L)
                .setPersonalNumericalCode("1234567891234")
                .setAddress("walls")
                .build();
        clientRepository.save(client);
        ClientAccount clientAccount = new ClientAccountBuilder()
                .setMoney(100)
                .setType("Visa")
                .setCreationDate(Date.valueOf(LocalDate.now()))
                .setId(clientRepository.findAll().get(0).getId())
                .setIdentificationNumber(1234567891234567L)
                .build();
        assertTrue(clientAccountRepository.save(clientAccount));
    }

    @Test
    public void editClient(){
        Client client = new ClientBuilder()
                .setName("test name")
                .setIdentityCardNumber(123456L)
                .setPersonalNumericalCode("1234567891234")
                .setAddress("walls")
                .build();
        clientRepository.save(client);
        Client clientEdited = new ClientBuilder()
                .setName("test")
                .setIdentityCardNumber(123456L)
                .setPersonalNumericalCode("1234567891234")
                .setAddress("walls")
                .build();
        regularUserService.editClient(clientEdited);
    }

    @Test
    public void editClientAccount(){
        Client client = new ClientBuilder()
                .setName("test name")
                .setIdentityCardNumber(123456L)
                .setPersonalNumericalCode("1234567891234")
                .setAddress("walls")
                .build();
        clientRepository.save(client);
        ClientAccount clientAccount = new ClientAccountBuilder()
                .setMoney(100)
                .setType("Visa")
                .setCreationDate(Date.valueOf(LocalDate.now()))
                .setId(clientRepository.findAll().get(0).getId())
                .setIdentificationNumber(1234567891234567L)
                .build();
        clientAccountRepository.save(clientAccount);
        ClientAccount clientEdited = new ClientAccountBuilder()
                .setMoney(10000)
                .setType("Visa")
                .setCreationDate(Date.valueOf(LocalDate.now()))
                .setId(clientRepository.findAll().get(0).getId())
                .setIdentificationNumber(1234567891234567L)
                .build();
        regularUserService.editClientAccount(clientEdited);
    }

    @Test
    public void deleteClientAccount(){
        Client client = new ClientBuilder()
                .setName("test name")
                .setIdentityCardNumber(123456L)
                .setPersonalNumericalCode("1234567891234")
                .setAddress("walls")
                .build();
        clientRepository.save(client);
        ClientAccount clientAccount = new ClientAccountBuilder()
                .setMoney(100)
                .setType("Visa")
                .setCreationDate(Date.valueOf(LocalDate.now()))
                .setId(clientRepository.findAll().get(0).getId())
                .setIdentificationNumber(1234567891234567L)
                .build();
        clientAccountRepository.save(clientAccount);
        regularUserService.deleteClientAccount(clientAccount);

    }

}
