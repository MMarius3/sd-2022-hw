package Controller;

import Database.JDBConnectionWrapper;
import Model.Builder.UserBuilder;
import Model.Role;
import Model.User;
import Model.Validator.UserValidator;
import Repository.Action.ActionRepositoryMySQL;
import Repository.Security.RightRolesRepositoryMySQL;
import Repository.Security.RightsRolesRepository;
import Service.User.AdminServiceMySQL;
import View.AdminView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static Database.Constants.Schemas.PRODUCTION;

public class AdminController {

    private final AdminView adminView;
    private final AdminServiceMySQL adminServiceMySQL;
    private final RightsRolesRepository rightRolesRepository;
    private User user;
    private UserValidator userValidator;

    public AdminController(AdminView adminView, AdminServiceMySQL adminServiceMySQL, RightsRolesRepository rightRolesRepository, UserValidator userValidator){
        this.adminServiceMySQL = adminServiceMySQL;
        this.adminView = adminView;
        this.rightRolesRepository = rightRolesRepository;
        this.userValidator = userValidator;
        this.adminView.setVisible(true);

        adminView.getAdd().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                userValidator.validate(adminView.getName().getText(),adminView.getPassword().getText());
                if(userValidator.getErrors().isEmpty()) {
                    User user = new UserBuilder()
                            .setUsername(adminView.getName().getText())
                            .setPassword(adminView.getPassword().getText())
                            .setRole(rightRolesRepository.findRoleById(2L))
                            .build();
                    adminServiceMySQL.addEmployee(user);
                    adminView.getUserTableView().getItems().clear();
                    adminView.getUserTableView().getItems().addAll(adminServiceMySQL.findAll());
                }
                else{
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setContentText(userValidator.getFormattedErrors());
                    error.show();
                }
            }
        });

        adminView.getView().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                adminView.getUserTableView().getItems().addAll(adminServiceMySQL.findAll());
                for(User u : adminServiceMySQL.findAll()){
                    adminView.getUserComboBox().getItems().add(u);
                    adminView.getUserComboBox().setConverter(new StringConverter<User>() {
                        @Override
                        public String toString(User user) {
                            return user.getId().toString();
                        }

                        @Override
                        public User fromString(String s) {
                            return null;
                        }
                    });
                }
            }
        });

        adminView.getDelete().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User user = adminView.getUserTableView().getSelectionModel().getSelectedItem();
                adminServiceMySQL.deleteEmployee(user);
                adminView.getUserTableView().getItems().clear();
                adminView.getUserTableView().getItems().addAll(adminServiceMySQL.findAll());
            }
        });

        adminView.getToUpdate().setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                user = adminView.getUserTableView().getSelectionModel().getSelectedItem();
                adminView.getName().setText(user.getUsername());
                adminView.getPassword().setText(user.getPassword());
            }
        }));

        adminView.getUpdate().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                user.setUsername(adminView.getName().getText());
                user.setPassword(adminView.getPassword().getText());
            }
        });

        adminView.getGenerateReport().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User u = adminView.getUserComboBox().getSelectionModel().getSelectedItem();
                Date start;
                Date end;
                LocalDate localDate = adminView.getStart().getValue();
                Calendar calendar = Calendar.getInstance();
                calendar.set(localDate.getYear(),localDate.getMonthValue()-1,localDate.getDayOfMonth());
                start = calendar.getTime();

                LocalDate localDateEnd = adminView.getFinish().getValue();
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.set(localDateEnd.getYear(),localDateEnd.getMonthValue()-1,localDateEnd.getDayOfMonth());
                end = calendarEnd.getTime();
                ActionRepositoryMySQL actionRepositoryMySQL = new ActionRepositoryMySQL(new JDBConnectionWrapper(PRODUCTION).getConnection());
                adminServiceMySQL.generateReport(u,start,end,actionRepositoryMySQL.findAll());
            }
        });
    }
}
