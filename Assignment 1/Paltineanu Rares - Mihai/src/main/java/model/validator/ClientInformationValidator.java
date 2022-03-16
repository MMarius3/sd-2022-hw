package model.validator;

import repository.client.ClientRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ClientInformationValidator {

    private final List<String> errors = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ClientInformationValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validate(String name, String personalNumericalCode) {
        errors.clear();
        //TODO
        // validateCardNumber(cardNumber);
        validatePersonalNumericalCode(personalNumericalCode);
        validatePersonalNumericalCodeBirthday(personalNumericalCode);
        validateName(name);
    }

    public void validateCardNumber(String cardNumber) {
        if (!cardNumber.matches("^(\\d{16})?$")) {
            errors.add("Card number must contain 16 digits");
        }
    }

    public void validatePersonalNumericalCode(String personalNumericalCode) {
        if (!personalNumericalCode.matches("^(\\d{13})?$")) {
            errors.add("Personal numerical code must contain 13 digits");
        }
    }

    public void validatePersonalNumericalCodeBirthday(String pnc) {
        int birthYear = Integer.parseInt(pnc.substring(1, 2));
        int birthMonth = Integer.parseInt(pnc.substring(3, 4));
        int birthDay = Integer.parseInt(pnc.substring(5, 6));

        if(birthMonth > 12) {
            errors.add("The month must be between 1 and 12");
        }
        Date date = new GregorianCalendar(birthYear, birthMonth, birthDay).getTime();
        LocalDate localDate = LocalDate.now();
//        localDate.get
    }

    private void idDateValid(Date date) {

    }

    private void validateName(String name) {
        if (name.matches(".*[0-9].*") || name.matches(".*[!@#$%^&*()_+].*")) {
            errors.add("The name must contain only alphabetic characters");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
