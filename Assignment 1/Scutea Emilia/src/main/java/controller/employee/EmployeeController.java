package controller.employee;

import view.LoginView;
import view.employee.*;

public class EmployeeController {
    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final EmployeeCRUDClientView employeeCRUDClientView;
    private final EmployeeCRUDAccountView employeeCRUDAccountView;
    private final EmployeeTransferMoneyView employeeTransferMoneyView;
    private final EmployeeBillsView employeeBillsView;

    public EmployeeController(LoginView loginView, EmployeeView employeeView, EmployeeCRUDClientView employeeCRUDClientView, EmployeeCRUDAccountView employeeCRUDAccountView, EmployeeTransferMoneyView employeeTransferMoneyView, EmployeeBillsView employeeBillsView) {
        this.loginView = loginView;
        this.employeeView = employeeView;
        this.employeeCRUDClientView = employeeCRUDClientView;
        this.employeeCRUDAccountView = employeeCRUDAccountView;
        this.employeeTransferMoneyView = employeeTransferMoneyView;
        this.employeeBillsView = employeeBillsView;

        CRUDAccountButtonAction();
        CRUDClientButtonAction();
        billsButtonAction();
        transferMoneyButtonAction();
        backButtonAction();
    }

    private void backButtonAction(){
        employeeView.getBackBtn().setOnAction(event -> {
            employeeView.setScene(loginView.getScene());
        });
    }


    public void CRUDClientButtonAction() {
        employeeView.getCrudClientButton().setOnAction(event -> {
            employeeView.setScene(employeeCRUDClientView.getScene());
        });
    }

    public void CRUDAccountButtonAction() {
        employeeView.getCrudAccountButton().setOnAction(event -> {
            employeeView.setScene(employeeCRUDAccountView.getScene());
        });
    }

    public void billsButtonAction() {
        employeeView.getBillsButton().setOnAction(event -> {
            employeeView.setScene(employeeBillsView.getScene());
        });
    }

    public void transferMoneyButtonAction() {
        employeeView.getTransferMoneyButton().setOnAction(event -> {
            employeeView.setScene(employeeTransferMoneyView.getScene());
        });
    }

}
