package model.validation;

import model.Account;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AccountValidator {
  private static final String BALANCE_REGEX = "^[0-9]{0,10}(\\.[0-9]{0,2})?$";
  private static final String DATA_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

  private final List<String> errors;
  private final Account account;

  public AccountValidator(Account account) {
    this.errors = new ArrayList<>();
    this.account = account;
  }

  public boolean validate() {
    if(!Pattern.compile(BALANCE_REGEX).matcher(String.valueOf(account.getBalance())).matches()) {
      errors.add("Balance must have 2 decimal places");
    }

    if(!Pattern.compile(DATA_REGEX).matcher(account.getCreationDate().toString()).matches()) {
      errors.add("Date must be in format yyyy-MM-dd");
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
