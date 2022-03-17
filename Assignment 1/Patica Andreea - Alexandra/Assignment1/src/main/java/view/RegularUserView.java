package view;

import controller.RegularUserController;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RegularUserView {

    ChoiceBox<String> choiceBox;
    RegularUserController controller = new RegularUserController();     //TODO

    public void display(Stage window){
        window.setTitle("Regular User");

        initializeChoiceBox();
        initializeVhoiceBoxAction();
        //HBox hbox = new HBox(choiceBox);


        Scene scene = new Scene(choiceBox, 300, 300);
        window.setScene(scene);
    }

    private void initializeChoiceBox(){
        choiceBox = new ChoiceBox<>();
        controller.initializeChoiceBox(choiceBox);      //TODO

        choiceBox.getItems().add("Choice 1");
        choiceBox.getItems().add("Choice 2");
        choiceBox.getItems().add("Choice 3");
    }

    private void initializeVhoiceBoxAction(){
        controller.setRegularUserView(this);
        choiceBox.setOnAction(controller.handleChoiceBoxListener());
    }
}
