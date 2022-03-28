package services.admin;

import model.validation.Notification;
import services.user.AuthenticationService;

public class AdminServiceImplementation implements AdminService{

    private final AuthenticationService authenticationService;

    public AdminServiceImplementation(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Notification<Boolean> createEmployee(String employeeUsername, String employeePassword) {
        return authenticationService.register(employeeUsername,employeePassword);
    }
}
