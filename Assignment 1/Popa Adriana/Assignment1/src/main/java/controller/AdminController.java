package controller;

import database.JDBConnectionWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Report;
import model.User;
import model.validator.UserValidator;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.report.ReportService;
import service.report.ReportServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static database.Constants.Schemas.PRODUCTION;

public class AdminController implements Initializable {

    @FXML private TextField id;
    @FXML private TextField username;
    @FXML private TextField password;

    @FXML private TextField from;
    @FXML private TextField to;

    @FXML private TableView<User> userTableView;
    @FXML private TableColumn<User,Long> idTableCell;
    @FXML private TableColumn<User,String> nameTableCell;
    @FXML private TableColumn<User,String> passwordTableCell;

    @FXML private TableView<Report> reportTableView;
    @FXML private TableColumn<Report,Integer> reportId;
    @FXML private TableColumn<Report,String> reportUser;
    @FXML private TableColumn<Report,String> reportAction;
    @FXML private TableColumn<Report, Date> reportDate;

    @FXML private TextArea textArea;

    private final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
    private final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    private final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    private final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    private final UserValidator userValidator = new UserValidator(userRepository);

    private final ReportRepository reportRepository = new ReportRepositoryMySQL(connection);
    private final ReportService reportService = new ReportServiceMySQL(reportRepository);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTableCell.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTableCell.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordTableCell.setCellValueFactory(new PropertyValueFactory<>("password"));

        reportId.setCellValueFactory(new PropertyValueFactory<>("id"));
        reportUser.setCellValueFactory(new PropertyValueFactory<>("employee"));
        reportAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        reportDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    private void createEmployee(){
        String usernameString = username.getText();
        String passwordString = password.getText();

        userValidator.validate(usernameString, passwordString);
        final List<String> errors = userValidator.getErrors();
        if (errors.isEmpty()) {
            authenticationService.register(usernameString, passwordString);
            textArea.setText("Successfully created employee!\n");
        } else {
            textArea.setText(userValidator.getFormattedErrors());
        }
    }

    @FXML
    private void viewEmployee(){
        if(!id.getText().isEmpty()) {
            Long idLong = Long.parseLong(id.getText());
            Optional<User> user = authenticationService.findById(idLong);
            if (user.isPresent()) {
                List<User> users = new ArrayList<>();
                users.add(user.get());
                userTableView.setItems(getObservableUsers(users));
            } else {
                textArea.setText("Employee not found!");
            }
        }
        else{
            textArea.setText("Introduce Id!");
        }
    }

    private ObservableList<User> getObservableUsers(List<User> users){
        ObservableList<User> observableUsers = FXCollections.observableArrayList();
        observableUsers.addAll(users);
        return observableUsers;
    }

    @FXML
    private void updateEmployee(){
        if(!id.getText().isEmpty()) {
            Long idLong = Long.parseLong(id.getText());
            String usernameString = username.getText();
            String passwordString = password.getText();
            userValidator.validate(usernameString, passwordString);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.update(idLong, usernameString, passwordString);
                textArea.setText("Successfully updated employee!\n");
            } else {
                textArea.setText(userValidator.getFormattedErrors());
            }
        }
        else{
            textArea.setText("Introduce Id!");
        }
    }

    @FXML
    private void deleteEmployee(){
        if(!username.getText().isEmpty()) {
            String usernameString = username.getText();
            boolean delete = authenticationService.delete(usernameString);
            if (delete) {
                textArea.setText("Successfully deleted!");
            } else {
                textArea.setText("Delete failed!");
            }
        }
        else{
            textArea.setText("Introduce username!");
        }
    }

    @FXML
    private void generateReports(){
        if(!from.getText().isEmpty()){
            if(!to.getText().isEmpty()){
                Date start = Date.valueOf(from.getText());
                Date end = Date.valueOf(to.getText());
                List<Report> reports = reportService.getReports(start,end);
                reportTableView.setItems(getObservableReports(reports));
            }
            else{
                textArea.setText("Introduce From Date!");
            }
        }
        else{
            textArea.setText("Introduce To Date!");
        }
    }

    private ObservableList<Report> getObservableReports(List<Report> reports){
        ObservableList<Report> observableReports = FXCollections.observableArrayList();
        observableReports.addAll(reports);
        return observableReports;
    }

    @FXML
    private void handleBack(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogIn.fxml"));
        LogInController controller = new LogInController(authenticationService, userValidator, rightsRolesRepository);
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Welcome!");
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

}