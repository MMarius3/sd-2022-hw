import Database.DBConnectionFactory;
import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Service.Account.AccountService;
import Service.Account.AccountServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceMySQLTest {


    private static AccountService accountService;
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        accountService = new AccountServiceMySQL(accountRepository);
        connection.isValid(10);
    }

    @BeforeEach
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Test
    public void add() throws  Exception{
        Long cardnr = 192L;
        String type = "savings";
        int amount = 2000;

        assertTrue(accountService.addAccount(cardnr,type,amount));
        assertNotNull(accountRepository.findByCardNr(cardnr));
    }

    @Test
    public void update() throws Exception {
        Long cardnr = 164L;
        String newType = "investment";
        int newAmount = 3000;

        accountService.addAccount(cardnr,"savings",2000);
        assertTrue(accountService.updateAccount(cardnr,newType,newAmount));
        assertEquals(accountRepository.findByCardNr(cardnr).getAmount(),newAmount);
        assertEquals(accountRepository.findByCardNr(cardnr).getType(),newType);
    }

    @Test
    public void view() throws Exception {
        assertNotNull(accountService.viewAccounts());
    }

    @Test
    public void delete() throws  Exception{
        Long cardnr = 162L;
        assertTrue(accountService.deleteAccount(cardnr));
        assertNull(accountRepository.findByCardNr(cardnr));
    }

    @Test
    public void transfer() throws  Exception{
        accountService.addAccount(143L,"savings",2000);
        accountService.addAccount(321L,"savings",1000);

        assertTrue(accountService.transferMoney(143L,321L,500));
        assertEquals(accountRepository.findByCardNr(143L).getAmount(),1500);
        assertEquals(accountRepository.findByCardNr(321L).getAmount(),1500);
    }

    @Test
    public void pay(){
        accountService.addAccount(123L,"savings",2000);
        accountService.payBill(123L,100);

        assertEquals(1900,accountRepository.findByCardNr(123L).getAmount());
    }
}
