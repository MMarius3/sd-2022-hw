package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Action;
import model.User;
import model.validator.UserValidator;
import repository.action.ActionRepository;
import repository.user.UserRepository;
import service.user.AdminActionService;
import view.AdminView;
import view.CreateUserView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {

    private final Scene adminScene;
    private AdminView adminView;
    private CreateUserView createUserView;
    private final AdminActionService adminActionService;
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;
    private final UserValidator userValidator;

    private final Stage stage;
    private final User user;
    private User userUpdate;

    private Scene createUserScene;


    public AdminController(User user, Stage stage, AdminView adminView, Scene adminScene, CreateUserView createUserView, AdminActionService adminActionService, UserRepository userRepository, ActionRepository actionRepository, UserValidator userValidator) {
        this.adminView = adminView;
        this.adminScene = adminScene;
        this.createUserView = createUserView;
        this.adminActionService = adminActionService;
        this.stage = stage;
        this.userRepository = userRepository;
        this.user = user;
        this.actionRepository = actionRepository;
        this.userValidator = userValidator;
        this.userUpdate = new User();
        initScenes();

        this.adminView.getCreate().setOnAction(actionEvent -> {
            stage.setScene(createUserScene);
        });

        this.createUserView.getSubmit().setOnAction(actionEvent -> {
            String username = this.createUserView.getUsername().getText();
            String password = this.createUserView.getPassword().getText();

            userValidator.validate(username,password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                adminActionService.createEmployee(username,password);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,userValidator.getFormattedErrors(), ButtonType.OK);
                alert.showAndWait();
            }
            try {
                adminActionService.createAction(user,"create","User " + user.getUsername() +" created a new user: "+ username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage.setScene(adminScene);


        });

        this.adminView.getShow().setOnAction(actionEvent -> {
            this.adminView.getUsers().getItems().clear();
            List<User> userList = userRepository.findAll();
            this.adminView.getUsers().getItems().addAll(userList);
        });

        this.adminView.getDelete().setOnAction(actionEvent -> {
            User userDelete = (User) this.adminView.getUsers().getSelectionModel().getSelectedItem();
            adminActionService.deleteEmployee(userDelete);

            this.adminView.getUsers().getItems().remove(userDelete);

            try {
                adminActionService.createAction(user,"delete","User " + user.getUsername() +" deleted the user: "+userDelete.getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        this.adminView.getUpdate().setOnAction(actionEvent -> {
            userUpdate = (User) this.adminView.getUsers().getSelectionModel().getSelectedItem();
            stage.setScene(createUserScene);
        });

        this.createUserView.getUpdate().setOnAction(actionEvent -> {
            String category = this.createUserView.getCategory().getSelectionModel().getSelectedItem();
            String username = this.createUserView.getUsername().getText();
            String password = this.createUserView.getPassword().getText();
            adminActionService.updateEmployee(category,userUpdate,username,password,null);
            try {
                adminActionService.createAction(user,"update","User " + user.getUsername() +" updated the user: "+ userUpdate.getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage.setScene(adminScene);
        });

        this.adminView.getGenerate().setOnAction(actionEvent -> {
            User userSel = this.adminView.getUsers().getSelectionModel().getSelectedItem();
            List<Action> actions = actionRepository.findAllWithId(userSel.getId());
            File file = new File("Reports.txt");
            try {
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fileWriter);
                for(Action action:actions){
                    bw.write(action.toString()+"\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        stage.setScene(adminScene);
        stage.show();
    }

    private void initScenes(){
        createUserView = new CreateUserView();
        createUserScene = new Scene(createUserView,270,250);
    }

}
