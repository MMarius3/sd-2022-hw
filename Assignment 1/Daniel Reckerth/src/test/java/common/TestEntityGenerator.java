package common;

import model.*;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.builder.EmployeeActivityBuilder;
import model.builder.UserBuilder;
import model.enums.AccountType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.format;

public class TestEntityGenerator {

  private static final Random RANDOM = new Random();

  public static User generateUserForTest(Long id) {
    final User user = new UserBuilder()
            .setUsername(format("username %d", id))
            .setPassword(format("password%d", id))
            .setRoles(new ArrayList<>())
            .build();

    if(id != 0) {
      user.setId(id);
    }

    return user;
  }

  public static Client generateClientForTest(Long id) {
    final Client client = new ClientBuilder()
            .setName(format("Client %d", id))
            .setCardNumber(format("01234567891234%d%d", RANDOM.nextInt(10), RANDOM.nextInt(10)))
            .setSSN(format("1850203356%d%d", RANDOM.nextInt(10), RANDOM.nextInt(10)))
            .setAddress(format("Address %d", id))
            .build();

    if(id != 0) {
      client.setId(id);
    }

    return client;
  }

  public static Account generateAccountForTest(Long id, Client client) {
    final Account account = new AccountBuilder()
            .setAccountType(AccountType.getRandomAccountType())
            .setBalance(RANDOM.nextDouble(10000))
            .setClient(client)
            .build();

    if(id != 0) {
      account.setId(id);
    }
    return account;
  }

  public static EmployeeActivity generateEmployeeActivityForTest(Long id, User employee, Right activity) {
    final EmployeeActivity employeeActivity = new EmployeeActivityBuilder()
            .setPerformedAt(LocalDateTime.now())
            .setEmployee(employee)
            .setPerformedActivity(activity)
            .build();

    if(id != 0) {
      employeeActivity.setId(id);
    }

    return employeeActivity;
  }

}
