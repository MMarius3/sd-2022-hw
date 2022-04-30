package controller.administrator;

import view.LoginView;
import view.administrator.AdministratorCRUDEmployeeView;
import view.administrator.AdministratorView;

public class AdministratorController {
    private final LoginView loginView;
    private final AdministratorView administratorView;
    private final AdministratorCRUDEmployeeView administratorCRUDEmployeeView;

    public AdministratorController(LoginView loginView, AdministratorView administratorView, AdministratorCRUDEmployeeView administratorCRUDEmployeeView) {
        this.loginView = loginView;
        this.administratorView = administratorView;
        this.administratorCRUDEmployeeView = administratorCRUDEmployeeView;
        backButtonAction();
        crudEmployeeAction();
    }

    private void backButtonAction() {
        administratorView.getBackBtn().setOnAction(event -> {
            administratorView.setScene(loginView.getScene());
        });
    }

    private void crudEmployeeAction(){
        administratorView.getCrudEmployee().setOnAction(event -> {
            administratorView.setScene(administratorCRUDEmployeeView.getScene());
        });
    }
}
