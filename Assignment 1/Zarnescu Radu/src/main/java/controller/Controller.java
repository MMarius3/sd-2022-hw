package controller;

import services.client.ClientService;
import services.user.AuthenticationService;

import java.sql.Connection;

public class Controller {
    private final ClientService clientService;
    private final AuthenticationService authenticationService;
    private final Connection connection;

    public Controller(ClientService clientService, AuthenticationService authenticationService, Connection connection) {
        this.clientService = clientService;
        this.authenticationService = authenticationService;
        this.connection = connection;
    }
}
