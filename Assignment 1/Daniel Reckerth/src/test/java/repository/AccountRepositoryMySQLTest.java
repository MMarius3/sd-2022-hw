package repository;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.*;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static common.TestEntityGenerator.generateAccountForTest;
import static common.TestEntityGenerator.generateClientForTest;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryMySQLTest {

  private static AccountRepository accountRepository;
  private static ClientRepository clientRepository;
  private static JDBConnectionWrapper connectionWrapper;

  @BeforeAll
  static void setUP() {
    connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
    clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
    accountRepository = new AccountRepositoryMySQL(connectionWrapper.getConnection(), clientRepository);

  }

  @AfterAll
  static void tearDown() throws SQLException {
    connectionWrapper.getConnection().close();
  }

  @BeforeEach
  void clean() {
    accountRepository.deleteAll();
    clientRepository.deleteAll();
  }

  @Test
  void findAll() {
    final List<Account> accounts = accountRepository.findAll();
    assertTrue(accounts.isEmpty());

    int nrInsertedAccounts = 10;
    for(int i = 1; i <= nrInsertedAccounts; i++) {
      Client client = generateClientForTest((long) i);
      Account account = generateAccountForTest((long) i, client);
      assertTrue(clientRepository.save(client));
      assertTrue(accountRepository.save(account));
    }

    final List<Account> insertedAccounts = accountRepository.findAll();
    assertEquals(nrInsertedAccounts, insertedAccounts.size());
  }

  @Test
  void findById_Empty_InvalidId() {
    final Optional<Account> user = accountRepository.findById(-1L);
    assertTrue(user.isEmpty());
  }

  @Test
  void findById_Present_ValidId() {
    Client client = generateClientForTest(1L);
    Account account = generateAccountForTest(1L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountRepository.save(account));

    final Optional<Account> accountOptional = accountRepository.findById(1L);
    assertTrue(accountOptional.isPresent());
    assertEquals(account.getAccountType(), accountOptional.get().getAccountType());
    assertEquals(account.getBalance(), accountOptional.get().getBalance());
    assertEquals(account.getCreationDate(), accountOptional.get().getCreationDate());
    assertEquals(account.getClient().getId(), accountOptional.get().getClient().getId());
  }

  @Test
  void save_invalid() {
    final Account invalidAccount = new AccountBuilder().build();
    assertFalse(accountRepository.save(invalidAccount));
  }

  @Test
  void save_valid() {
    final Client client = generateClientForTest(1L);
    final Account noGivenIdAccount = generateAccountForTest(0L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountRepository.save(noGivenIdAccount));

    final Account givenIdAccount = generateAccountForTest(13L, client);
    assertTrue(accountRepository.save(givenIdAccount));
    assertTrue(accountRepository.findById(13L).isPresent());
  }

  @Test
  void update() {
    final Client client = generateClientForTest(1L);
    final Account account = generateAccountForTest(1L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountRepository.save(account));

    final Account updateAccount = generateAccountForTest(1L, client);
    assertNotEquals(account.getBalance(), updateAccount.getBalance());
    assertTrue(accountRepository.update(1L, updateAccount));
    final Optional<Account> accountOptional = accountRepository.findById(1L);
    assertTrue(accountOptional.isPresent());
    assertEquals(updateAccount.getBalance(), accountOptional.get().getBalance());

  }

  @Test
  void deleteById() {
    final Client client = generateClientForTest(1L);
    final Account account = generateAccountForTest(1L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountRepository.save(account));
    assertTrue(accountRepository.deleteById(1L));

    assertTrue(accountRepository.findById(1L).isEmpty());
  }

  @Test
  void deleteAll() {
    int nrInsertedAccounts = 10;
    for(int i = 1; i <= nrInsertedAccounts; i++) {
      Client client = generateClientForTest((long) i);
      Account account = generateAccountForTest((long) i, client);
      assertTrue(clientRepository.save(client));
      assertTrue(accountRepository.save(account));
    }

    accountRepository.deleteAll();
    final List<Account> allAccount = accountRepository.findAll();
    assertTrue(allAccount.isEmpty());
  }

  @Test
  void findAccountsByClientId() {
    List<Account> accountsOfInvalidUser = accountRepository.findAccountsByClientId(-1L);
    assertTrue(accountsOfInvalidUser.isEmpty());

    Client client = generateClientForTest(1L);
    List<Account> accounts = Arrays.asList(generateAccountForTest(1L, client), generateAccountForTest(2L, client), generateAccountForTest(3L, client));
    assertTrue(clientRepository.save(client));
    accounts.forEach(account -> assertTrue(accountRepository.save(account)));

    List<Account> accountsForClient = accountRepository.findAccountsByClientId(1L);
    assertEquals(3, accountsForClient.size());
  }
}