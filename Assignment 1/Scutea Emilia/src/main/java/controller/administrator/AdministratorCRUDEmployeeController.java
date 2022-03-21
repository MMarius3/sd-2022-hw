package controller.administrator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.administrator.AdministratorCRUDEmployeeView;
import view.administrator.AdministratorView;

import java.util.List;

public class AdministratorCRUDEmployeeController {
    private final AdministratorView administratorView;
    private final AdministratorCRUDEmployeeView administratorCRUDEmployeeView;
    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    private ObservableList<User> observableListView = FXCollections.observableArrayList();


    public AdministratorCRUDEmployeeController(AdministratorView administratorView, AdministratorCRUDEmployeeView administratorCRUDEmployeeView, UserValidator userValidator, AuthenticationService authenticationService) {
        this.administratorView = administratorView;
        this.administratorCRUDEmployeeView = administratorCRUDEmployeeView;
        this.userValidator = userValidator;
        this.authenticationService = authenticationService;
        backButtonAction();
        addButtonAction();
        updateButtonAction();
        viewButtonAction();
    }

    private void backButtonAction() {
        administratorCRUDEmployeeView.getBackBtn().setOnAction(event -> {
            administratorCRUDEmployeeView.setScene(administratorView.getScene());
        });
    }

    private void addButtonAction() {
        administratorCRUDEmployeeView.getAddBtn().setOnAction(event -> {
            if (!checkEmptyInputEmployeeAdd()) {

                String username = administratorCRUDEmployeeView.getUsernameAdd();
                String password = administratorCRUDEmployeeView.getPasswordAdd();

                userValidator.validateRegister(username, password);
                final List<String> errors = userValidator.getErrors();

                if (errors.isEmpty()) {
                    authenticationService.register(username, password);
                } else {
                    for (String error : errors) {
                        System.out.println(error);
                    }
                }
            }
        });
    }

    private void updateButtonAction() {
        administratorCRUDEmployeeView.getUpdateBtn().setOnAction(event -> {
            if (!checkEmptyInputEmployeeUpdate()) {

                String username = administratorCRUDEmployeeView.getUsernameUpdate();
                String password = administratorCRUDEmployeeView.getPasswordUpdate();
                Long id = Long.parseLong(administratorCRUDEmployeeView.getIdUpdate());

                userValidator.validateUpdate(username, password);
                final List<String> errors = userValidator.getErrors();

                if (errors.isEmpty()) {
                    User user = authenticationService.findById(id);
                    if (user != null) {
                        user.setUsername(username);
                        user.setPassword(password);
                        authenticationService.update(user);
                    } else {
                        errors.add("Client with id %d does not exist".formatted(id));
                        for (String error : errors) {
                            System.out.println(error);
                        }
                    }
                } else {
                    for (String error : errors) {
                        System.out.println(error);
                    }
                }
            }
        });
    }

    private void viewButtonAction() {
        administratorCRUDEmployeeView.getViewEmployeesBtn().setOnAction(event -> {
            List<User> users = authenticationService.findAll();
            observableListView.clear();

            try {
                for (User user : users) {
                    observableListView.add(new UserBuilder()
                            .setUserName(user.getUsername())
                            .setId(user.getId())
                            .setPassword(user.getPassword())
                            .setRoles(user.getRoles())
                            .build());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            administratorCRUDEmployeeView.getTable_id().setCellValueFactory(new PropertyValueFactory<>("id"));
            administratorCRUDEmployeeView.getTable_username().setCellValueFactory(new PropertyValueFactory<>("username"));
            administratorCRUDEmployeeView.getTable_password().setCellValueFactory(new PropertyValueFactory<>("password"));
            administratorCRUDEmployeeView.getTable_role().setCellValueFactory(new PropertyValueFactory<>("roles"));
            administratorCRUDEmployeeView.getTable().setItems(observableListView);
        });
    }


    private boolean checkEmptyInputEmployeeAdd() {
        return administratorCRUDEmployeeView.getUsernameAdd().equals("") || administratorCRUDEmployeeView.getPasswordAdd().equals("");
    }

    private boolean checkEmptyInputEmployeeUpdate() {
        return administratorCRUDEmployeeView.getUsernameUpdate().equals("") || administratorCRUDEmployeeView.getPasswordUpdate().equals("") || administratorCRUDEmployeeView.getIdUpdate().equals("");
    }


}
