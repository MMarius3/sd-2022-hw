package validator;

import dtos.AccountDTO;

public class AccountDTOValidator implements Validator {

    private AccountDTO accountDTO;

    public AccountDTOValidator(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    @Override
    public boolean validate() {
        return validateSum()
                && validateIdentificationNumber();
    }

    private boolean validateSum() {
        return this.accountDTO.getSum() > 0;
    }

    private boolean validateIdentificationNumber() {
        return this.accountDTO.getIdentificationNumber().length() < 255;
    }
}
