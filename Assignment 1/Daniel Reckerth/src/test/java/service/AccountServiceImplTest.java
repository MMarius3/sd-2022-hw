package service;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static common.TestEntityGenerator.generateAccountForTest;
import static common.TestEntityGenerator.generateClientForTest;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

  private static AccountService accountService;
  private static ClientRepository clientRepository;

  @BeforeAll
  public static void setup() {
    Connection connection = DBConnectionFactory.getConnectionWrapper(true).getConnection();
    clientRepository = new ClientRepositoryMySQL(connection);
    AccountRepository accountRepository = new AccountRepositoryMySQL(connection, clientRepository);
    accountService = new AccountServiceImpl(accountRepository);
  }

  @BeforeEach
  public void cleanup() {
    clientRepository.deleteAll();
    accountService.deleteAll();
  }

  @Test
  void findAll() {
    final List<Account> accounts = accountService.findAll();
    assertTrue(accounts.isEmpty());

    int nrInsertedAccounts = 10;
    for(int i = 1; i <= nrInsertedAccounts; i++) {
      Client client = generateClientForTest((long) i);
      Account account = generateAccountForTest((long) i, client);
      assertTrue(clientRepository.save(client));
      assertTrue(accountService.save(account));
    }

    final List<Account> insertedAccounts = accountService.findAll();
    assertEquals(nrInsertedAccounts, insertedAccounts.size());
  }

  @Test
  void findById() {
    final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> accountService.findById(1L));
    assertEquals("No such id", illegalArgumentException.getMessage());

    Client client = generateClientForTest(1L);
    Account account = generateAccountForTest(1L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountService.save(account));

    final Account receivedAccount = accountService.findById(1L);
    assertNotNull(receivedAccount);
    assertEquals(receivedAccount.getAccountType(), receivedAccount.getAccountType());
    assertEquals(receivedAccount.getBalance(), receivedAccount.getBalance());
    assertEquals(receivedAccount.getCreationDate(), receivedAccount.getCreationDate());
    assertEquals(receivedAccount.getClient().getId(), receivedAccount.getClient().getId());
  }

  @Test
  void save() {
    final Account invalidAccount = new AccountBuilder().build();
    assertFalse(accountService.save(invalidAccount));

    final Client client = generateClientForTest(1L);
    final Account noGivenIdAccount = generateAccountForTest(0L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountService.save(noGivenIdAccount));

    final long id = 13L;
    final Account givenIdAccount = generateAccountForTest(id, client);
    assertTrue(accountService.save(givenIdAccount));
    assertEquals(id, accountService.findById(id).getId());
  }

  @Test
  void update() {
    final Client client = generateClientForTest(1L);
    final Account account = generateAccountForTest(1L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountService.save(account));

    final Account updateAccount = generateAccountForTest(1L, client);
    assertNotEquals(account.getBalance(), updateAccount.getBalance());
    assertTrue(accountService.update(1L, updateAccount));
    final Account accountReceived = accountService.findById(1L);
    assertNotNull(accountReceived);
    assertEquals(updateAccount.getBalance(), accountReceived.getBalance());
  }

  @Test
  void deleteById() {
    final Client client = generateClientForTest(1L);
    final Account account = generateAccountForTest(1L, client);
    assertTrue(clientRepository.save(client));
    assertTrue(accountService.save(account));
    assertTrue(accountService.deleteById(1L));

    final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> accountService.findById(1L));
    assertEquals("No such id", illegalArgumentException.getMessage());
  }

  @Test
  void deleteAll() {
    int nrInsertedAccounts = 10;
    for(int i = 1; i <= nrInsertedAccounts; i++) {
      Client client = generateClientForTest((long) i);
      Account account = generateAccountForTest((long) i, client);
      assertTrue(clientRepository.save(client));
      assertTrue(accountService.save(account));
    }

    accountService.deleteAll();
    assertTrue(accountService.findAll().isEmpty());
  }

  @Test
  void findAccountsByClientId() {
    List<Account> accountsOfInvalidUser = accountService.findAccountsByClientId(-1L);
    assertTrue(accountsOfInvalidUser.isEmpty());

    Client client = generateClientForTest(1L);
    List<Account> accounts = Arrays.asList(generateAccountForTest(1L, client), generateAccountForTest(2L, client), generateAccountForTest(3L, client));
    assertTrue(clientRepository.save(client));
    accounts.forEach(account -> assertTrue(accountService.save(account)));

    List<Account> accountsForClient = accountService.findAccountsByClientId(1L);
    assertEquals(3, accountsForClient.size());
  }
}