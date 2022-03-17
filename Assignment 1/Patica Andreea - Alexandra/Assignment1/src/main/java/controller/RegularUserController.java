package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import view.ClientView;
import view.ErrorView;
import view.RegularUserView;

public class RegularUserController {
    RegularUserView regularUserView;

    public void initializeChoiceBox(ChoiceBox<String> choiceBox){

    }

    public void setRegularUserView(RegularUserView regularUserView){
        this.regularUserView = regularUserView;
    }

    public EventHandler<ActionEvent> handleChoiceBoxListener(){
        return e -> {
            //TODO
            new ClientView().display( );
        };
    }
}
