package model.validation;

import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientValidatorTest {
  private final String[] invalidCNPs = {"09920323521", // cannot start with 0
          "1991320323521", // month 13 does not exist
          "1990532254926", // day 32 does not exist
          "1234", // less than 13 digits
  };
  private final String validCNP = "1960521322569";
  private final String validCardNumber="1234123412341234";
  private final String validName = "John Doe";
  private final String validAddress = "Bucuresti, Romania";


  @Test
  void invalidCNPClients() {
    List<Client> invalidCNPClients = new ArrayList<>();
    for(String invalidCNP : invalidCNPs) {
      Client invalidClient = new ClientBuilder().setAddress(validAddress)
              .setName(validName)
              .setCardNumber(validCardNumber)
              .setSSN(invalidCNP)
              .build();
      invalidCNPClients.add(invalidClient);
    }

    for(Client invalidCNPClient : invalidCNPClients) {
      ClientValidator validator = new ClientValidator(invalidCNPClient);
      assertFalse(validator.validate());
      assertFalse(validator.getErrors().isEmpty());
    }
  }

  @Test
  void invalidCardNumberClient() {
    // less than 16 digits
    String invalidCardNumber = "123412341234123";
    Client invalidCardNumberClient = new ClientBuilder()
            .setCardNumber(invalidCardNumber)
            .setName(validName)
            .setSSN(validCNP)
            .setAddress(validAddress)
            .build();
    ClientValidator validator = new ClientValidator(invalidCardNumberClient);
    assertFalse(validator.validate());
    assertFalse(validator.getErrors().isEmpty());
  }

  @Test
  void invalidAddressClient() {
    Client invalidAddressClient = new ClientBuilder()
            .setCardNumber(validCardNumber)
            .setName(validName)
            .setSSN(validCNP)
            .setAddress("")
            .build();
    ClientValidator validator = new ClientValidator(invalidAddressClient);
    assertFalse(validator.validate());
    assertFalse(validator.getErrors().isEmpty());
  }

  @Test
  void invalidNameClient() {
    Client invalidNameClient = new ClientBuilder()
            .setCardNumber(validCardNumber)
            .setName("")
            .setSSN(validCNP)
            .setAddress(validAddress)
            .build();
    ClientValidator validator = new ClientValidator(invalidNameClient);
    assertFalse(validator.validate());
    assertFalse(validator.getErrors().isEmpty());
  }

  @Test
  void validClient() {
    Client validClient = new ClientBuilder()
            .setCardNumber(validCardNumber)
            .setName(validName)
            .setSSN(validCNP)
            .setAddress(validAddress)
            .build();
    ClientValidator validator = new ClientValidator(validClient);
    assertTrue(validator.validate());
    assertTrue(validator.getErrors().isEmpty());
  }
}