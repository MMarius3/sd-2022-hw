package service.account;

import database.DBConnectionFactory;
import model.validator.AccountValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMock;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMock;
import repository.client_account.ClientAccountRepositoryMock;
import repository.client_account.ClientAccountRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceImplTest {

    private AccountService accountService;

    @BeforeEach
    public void setup() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountService = new AccountServiceImpl(new AccountRepositoryMySQL(connection), new ClientAccountRepositoryMySQL(connection));
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, accountService.findAll().size());
    }

    @Test
    public void findById() throws Exception {
        Long id = 1L;
        assertThrows(IllegalArgumentException.class, () -> accountService.findById(id));
    }

    @Test
    public void addAccount() throws Exception {
        String type = "savings";
        Integer moneyAmount = 1000;
        Long clientId = 1L;
        String type1 = "investments";
        Integer moneyAmount1 = 1001;
        Long clientId1 = 2L;
        assertTrue(accountService.addAccount(type, moneyAmount, clientId) &&
                accountService.addAccount(type1, moneyAmount1, clientId1));
    }

    @Test
    public void updateAccount() throws Exception {
        Long id = 1L;
        String type = "investments";
        Integer moneyAmount = 1000;
        assertTrue(accountService.updateAccount(id, type, moneyAmount));
    }

    @Test
    public void viewAccount() throws Exception {
        Long id = 3L;
        assertTrue(accountService.viewAccount(id) != null);
    }

    @Test
    public void transferMoney() throws Exception {
        Long id1 = 1L;
        Long id2 = 2L;
        Integer moneyAmountToTransfer = 7;
        Long id11 = 2L;
        Long id21 = 1L;
        Integer moneyAmountToTransfer1 = 17;
        assertTrue(accountService.transferMoney(id1, id2, moneyAmountToTransfer) &&
                accountService.transferMoney(id11, id21, moneyAmountToTransfer1));
    }

    @Test
    public void executeBill() throws Exception {
        Long id = 1L;
        Integer billToExecute = 17;
        Integer billToExecute1 = 13;
        assertTrue(accountService.executeBill(id, billToExecute) &&
                accountService.executeBill(id, billToExecute1));
    }

}