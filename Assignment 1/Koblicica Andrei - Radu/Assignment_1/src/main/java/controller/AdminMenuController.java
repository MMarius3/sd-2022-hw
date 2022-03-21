package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Action;
import model.Client;
import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import service.user.UserService;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.logging.XMLFormatter;

public class AdminMenuController {


    @FXML
    private TableView<User> employeeTable;

    @FXML
    private TableColumn<User, Long> idColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private Label actionLabel;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    private Button submitEmployeeButton;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button editEmployeeButton;
    @FXML
    private Button deleteEmployeeButton;
    @FXML
    private Button cancelButton;


    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button generateReportButton;

    @FXML
    private TableView actionTable;
    @FXML
    private TableColumn actionIdColumn;
    @FXML
    private TableColumn actionDescriptionColumn;
    @FXML
    private TableColumn actionDateColumn;




    private User selectedEmployee;

    private UserService userService;
    private AuthenticationService authenticationService;
    private UserValidator userValidator;



    public void initializeScene(UserService userService, AuthenticationService authenticationService, UserValidator userValidator) {
        this.userService=userService;
        this.userValidator=userValidator;
        this.authenticationService=authenticationService;
        setVisibility(false);
        initializeTable();
    }

    private void initializeTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        actionIdColumn.setCellValueFactory(new PropertyValueFactory<Action, Long>("id"));
        actionDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Action, String>("description"));
        actionDateColumn.setCellValueFactory(new PropertyValueFactory<Action, Date>("date"));

        employeeTable.setItems(userService.findAllEmployees());
    }

    private void setVisibility(boolean visibility){
        usernameLabel.setVisible(visibility);
        usernameField.setVisible(visibility);
        passwordField.setVisible(visibility);
        passwordLabel.setVisible(visibility);
        submitEmployeeButton.setVisible(visibility);
        if(!visibility){
            actionLabel.setText("");
        }
    }

    private void clearFields(){
        usernameField.clear();
        passwordField.clear();
        startDatePicker.getEditor().clear();
        endDatePicker.getEditor().clear();
    }


    @FXML
    private void pressAddEmployee(ActionEvent event){
        setVisibility(true);
        clearFields();
        actionLabel.setText("Add new employee:");

    }
    @FXML
    private void pressEditEmployee(ActionEvent event){
        if(employeeTable.getSelectionModel().getSelectedItem()!=null){
            selectedEmployee=employeeTable.getSelectionModel().getSelectedItem();
            setVisibility(true);
            actionLabel.setText("Edit employee:");
            usernameField.setText(selectedEmployee.getUsername());
            passwordField.setText("");
        }

    }
    @FXML

    private void pressDeleteEmployee(ActionEvent event){
        if(employeeTable.getSelectionModel().getSelectedItem()!=null){
            userService.remove(employeeTable.getSelectionModel().getSelectedItem());
            employeeTable.setItems(userService.findAllEmployees());
        }
    }
    @FXML

    private void pressCancel(ActionEvent event){
        setVisibility(false);
        clearFields();
        actionTable.getItems().clear();
    }
    @FXML
    private void pressSubmitEmployee(ActionEvent event){
        String username=usernameField.getText();
        String password=passwordField.getText();
        if(actionLabel.getText().equals("Add new employee:")){
            submitAddEmployee(username,password);
        }else{
            if(actionLabel.getText().equals("Edit employee:")){
                submitEditEmployee(username,password);
            }
        }
    }

    private void submitEditEmployee(String username, String password) {
        boolean checkUniqueness=!selectedEmployee.getUsername().equals(username);
        userValidator.validate(username, password,checkUniqueness);
        final String errors = userValidator.getFormattedErrors();
        if(errors.isEmpty()){
            User user = new UserBuilder()
                    .setId(selectedEmployee.getId())
                    .setUsername(username)
                    .setPassword(authenticationService.encodePassword(password))
                    .build();
            userService.edit(user);
            employeeTable.setItems(userService.findAllEmployees());
            setVisibility(false);
            clearFields();
        }
    }

    private void submitAddEmployee(String username, String password) {
        userValidator.validate(username, password, true);
        final String errors = userValidator.getFormattedErrors();
        if(errors.isEmpty()){
            authenticationService.register(username,password);
            employeeTable.setItems(userService.findAllEmployees());
            setVisibility(false);
            clearFields();
        }else{
            loadAlertBox(errors);
        }
    }

    @FXML
    private void pressGenerate(ActionEvent event){
    if(employeeTable.getSelectionModel().getSelectedItem()!=null){
        if(startDatePicker.getValue()==null || endDatePicker.getValue()==null){
            loadAlertBox("Make sure you complete all fields!");
        }
        Long userId=employeeTable.getSelectionModel().getSelectedItem().getId();
        Date startDate=Date.valueOf(startDatePicker.getValue());
        Date endDate=Date.valueOf(endDatePicker.getValue());
        ObservableList<Action> actions=userService.findAllActions(userId,startDate,endDate);
        actionTable.setItems(actions);
        userService.generateReport(employeeTable.getSelectionModel().getSelectedItem(),actions, startDate, endDate);
    }

    }


    private void loadAlertBox(String errors){
        Stage stage=new Stage();
        stage.setTitle("Error");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AlertBox.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertBoxController alertBoxController=loader.getController();
        alertBoxController.initializeLabel(errors);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }



}
