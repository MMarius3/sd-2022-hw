package view.component;

import model.Client;

import javax.swing.*;
import java.util.List;

public class ClientsDetailsBuilder extends ComponentsFetcher{

    private JTextField clientIDTextField;
    private JTextField clientNameTextField;
    private JTextField identityCardNumberTextField;
    private JTextField personalNumericCodeTextField;
    private JTextField addressTextField;

    public ClientsDetailsBuilder(List<Client> clients) {

    }

    public ClientsDetailsBuilder setClientIDTextField(JTextField clientIDTextField) {
        clientIDTextField = new JTextField();
        this.clientIDTextField = clientIDTextField;
        return this;
    }

    public ClientsDetailsBuilder setClientNameTextField(JTextField clientNameTextField) {
        this.clientNameTextField = clientNameTextField;
        return this;
    }

    public ClientsDetailsBuilder setIdentityCardNumberTextField(JTextField identityCardNumberTextField) {
        this.identityCardNumberTextField = identityCardNumberTextField;
        return this;
    }

    public ClientsDetailsBuilder setPersonalNumericCodeTextField(JTextField personalNumericCodeTextField) {
        this.personalNumericCodeTextField = personalNumericCodeTextField;
        return this;
    }

    public ClientsDetailsBuilder setAddressTextField(JTextField addressTextField) {
        this.addressTextField = addressTextField;
        return this;
    }
}
