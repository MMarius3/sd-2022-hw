package Controller;

import Model.Event;
import Model.User;
import Model.Validator.EventValidator;
import Model.Validator.UserValidator;
import Service.Employee.EmployeeService;
import Service.Event.EventService;
import View.AdminView;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.util.List;

public class AdminController {

    private final AdminView adminView;
    private final EventService eventService;
    private final EmployeeService employeeService;
    private final UserValidator userValidator;
    private final EventValidator eventValidator;

    public AdminController( AdminView adminView, EventService eventService, EmployeeService employeeService, UserValidator userValidator, EventValidator eventValidator) {
        this.adminView = adminView;
        this.eventService = eventService;
        this.employeeService = employeeService;
        this.userValidator = userValidator;
        this.eventValidator = eventValidator;

        addEmployeeAction();
        updateEmployeeAction();
        deleteEmployeeAction();
        viewEmployeesAction();
        generateReportAction();
    }

    public void addEmployeeAction(){
        adminView.getAddEmployee().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            String username = adminView.getUsername().getText();
            String password = adminView.getPassword().getText();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) employeeService.addEmp(username, password);
            else adminView.getErrorMsg().setText(userValidator.getFormattedErrors());
            clearFieldsAdmin();
        });
    }

    public void deleteEmployeeAction(){
        adminView.getDeleteEmployee().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            String username = adminView.getUsername().getText();

            userValidator.validateDelete(username);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) employeeService.deleteEmp(username);
            else adminView.getErrorMsg().setText(userValidator.getFormattedErrors());
            clearFieldsAdmin();
        });
    }

    public void updateEmployeeAction(){
        adminView.getUpdateEmployee().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            String id = adminView.getEmployeeId().getText();
            String username = adminView.getUsername().getText();
            String password = adminView.getPassword().getText();

            userValidator.validateUpdate(id,username,password);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) employeeService.updateEmp(Long.parseLong(id),username,password);
            else adminView.getErrorMsg().setText(userValidator.getFormattedErrors());
            clearFieldsAdmin();
        });
    }

    public void viewEmployeesAction(){
        adminView.getViewEmployees().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            adminView.getVbox().getChildren().clear();
            clearFieldsAdmin();
            adminView.getVbox().getChildren().add(adminView.createTable(employeeService.viewEmp()));
        });
    }

    public void generateReportAction(){
        adminView.getGenerateReport().setOnAction(e->{
            adminView.getVbox().getChildren().clear();
            adminView.getErrorMsg().setText("");
            String dateFrom = adminView.getDateFrom().getText();
            String dateTo = adminView.getDateTo().getText();
            String username = adminView.getUsername2().getText();

            eventValidator.validate(username,dateFrom,dateTo);
            final List<String> errors = eventValidator.getErrors();
            if(errors.isEmpty()){
                ObservableList<Event> events = eventService.findByUsernameAndDates(Date.valueOf(dateFrom),Date.valueOf(dateTo),username);
                adminView.getVbox().getChildren().add(adminView.createTable(events));
            }else adminView.getErrorMsg().setText(eventValidator.getFormattedErrors());
            clearFieldsAdmin();
        });
    }

    public void clearFieldsAdmin(){
        adminView.getEmployeeId().clear();
        adminView.getUsername().clear();
        adminView.getPassword().clear();
        adminView.getUsername2().clear();
        adminView.getDateTo().clear();
        adminView.getDateFrom().clear();
    }
}
