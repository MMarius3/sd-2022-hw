package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class AlertBoxController {

    @FXML
    private Label messageLabel;

    @FXML
    private Button OK;

    public void initializeLabel(String errors){
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setText(errors);  //displaying the message that came as parameter
    }

    public void pressOK(ActionEvent event){//user can close the warning by hitting ok
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}