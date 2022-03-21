package validator;

import dtos.ClientDTO;

public class ClientDTOValidator implements Validator {

    private ClientDTO clientDTO;

    public ClientDTOValidator(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    @Override
    public boolean validate() {
        return validateName()
                && validateAddress()
                && validateIdentityCardNumber()
                && validatePersonalNumericCode();
    }

    private boolean validateName() {
        return this.clientDTO.getName().length() < 255;
    }

    private boolean validateAddress() {
        return this.clientDTO.getAddress().length() < 255;
    }

    private boolean validateIdentityCardNumber() {
        return this.clientDTO.getIdentityCardNumber().length() < 255;
    }

    private boolean validatePersonalNumericCode() {
        return this.clientDTO.getPersonalNumericCode().matches("^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$");
    }
}
