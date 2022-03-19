package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import model.User;
import view.ClientView;
import view.MainUI;
import view.MessageView;
import view.RegularUserView;

public class UserController {
    RegularUserView regularUserView;
    User user;

    public UserController(User user){
        this.user = user;
        regularUserView = new RegularUserView();
        RegularUserView.setController(this);
        //initializeChoiceBox(regularUserView.getChoiceBox());
        regularUserView.display(MainUI.getWindow());
    }



    public void setRegularUserView(RegularUserView regularUserView){
        this.regularUserView = regularUserView;
    }


    public EventHandler<ActionEvent> handleSearchButtonListener(){
        return e -> {
            Integer clientId = Integer.getInteger(regularUserView.getIdTextField().toString());
            String clientName = regularUserView.getNameTextField().toString();


        };
    }
}
