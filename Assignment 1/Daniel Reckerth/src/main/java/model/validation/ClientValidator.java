package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {
  private static final String CNP_REGEX = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
  private static final String CARD_NUMBER_REGEX = "^[0-9]{16}$";

  private final List<String> errors;
  private final Client client;

  public ClientValidator(Client client) {
    this.client = client;
    this.errors = new ArrayList<>();
  }

  public boolean validate() {
    if(!Pattern.compile(CNP_REGEX).matcher(client.getSSN()).matches()) {
      errors.add("SSN is invalid");
    }
    if(!Pattern.compile(CARD_NUMBER_REGEX).matcher(client.getCardNumber()).matches()) {
      errors.add("Card number is invalid");
    }
    if(client.getAddress() == null || client.getAddress().isEmpty()) {
      errors.add("Address cannot be empty");
    }
    if(client.getName() == null || client.getName().isEmpty()) {
      errors.add("Name cannot be empty");
    }

    return errors.isEmpty();
  }

  public List<String> getErrors() {
    return errors;
  }

  public String getFormattedErrors() {
    return String.join("\n", errors);
  }
}
