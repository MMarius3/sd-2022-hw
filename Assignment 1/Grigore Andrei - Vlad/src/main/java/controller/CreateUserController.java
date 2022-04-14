package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import repository.action.ActionRepository;
import view.AdminView;
import view.CreateUserView;

public class CreateUserController {

    private final CreateUserView createUserView;
    private final ActionRepository actionRepository;
    private final Stage stage;
    private final AdminView adminView;
    private Scene adminScene;

    public CreateUserController(CreateUserView createUserView, ActionRepository actionRepository, Stage stage, AdminView adminView) {
        this.createUserView = createUserView;
        this.actionRepository = actionRepository;
        this.stage = stage;
        this.adminView = adminView;
        initScenes();
        initButtons();
    }

    private void initScenes(){
        adminScene = new Scene(adminView,800,600);
    }
    private void initButtons(){
        this.createUserView.getSubmit().setOnAction(actionEvent -> {
            stage.setScene(adminScene);
        });
    }
}
