package controller.employee;

import model.Account;
import model.validator.AccountValidator;
import service.account.AccountService;
import view.employee.EmployeeBillsView;
import view.employee.EmployeeView;

import java.util.List;

public class EmployeeBillsController {
    private final EmployeeBillsView employeeBillsView;
    private final AccountService accountService;
    private final AccountValidator accountValidator;
    private final EmployeeView employeeView;

    public EmployeeBillsController(EmployeeView employeeView,EmployeeBillsView employeeBillsView, AccountService accountService, AccountValidator accountValidator) {
        this.employeeBillsView = employeeBillsView;
        this.accountService = accountService;
        this.accountValidator = accountValidator;
        this.employeeView=employeeView;

        payBillsButtonAction();
        backButtonAction();
    }

    private void backButtonAction(){
        employeeBillsView.getBackBtn().setOnAction(event -> {
            employeeBillsView.setScene(employeeView.getScene());
        });
    }

    private void payBillsButtonAction(){
        employeeBillsView.getPayBillsBtn().setOnAction(event -> {
            if(!checkEmptyInputPayBills()){
                Long fromId = Long.parseLong(employeeBillsView.getPayFromId());
                Double amount = Double.parseDouble(employeeBillsView.getAmount());

                accountValidator.validatePayBill(fromId, amount);
                final List<String> errors = accountValidator.getErrors();

                if(errors.isEmpty()){
                    Account fromAccount = accountService.findById(fromId);
                    fromAccount.setAmount(fromAccount.getAmount()-amount);
                    accountService.update(fromAccount);
                }
                else{
                    for(String error:errors){
                        System.out.println(error);
                    }
                }
            }
        });
    }

    private boolean checkEmptyInputPayBills(){
        return employeeBillsView.getPayFromId().equals("") || employeeBillsView.getAmount().equals("");
    }
}
