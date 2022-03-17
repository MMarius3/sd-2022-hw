package view;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientView {

    private Text name;
    private Text idNumber;
    private Text personalIdentificationCode;
    private Text address;
    private Text accounts;

    private TextField nameField;
    private TextField idNumberField;
    private TextField personalIdentificationCodeField;
    private TextField addressField;
    private ChoiceBox<String> accountsBox;

    public void display(){
        Stage window = new Stage();
        window.setTitle("Client");



        window.setScene(new Scene(new VBox(), 280, 100));
        window.setResizable(false);
        window.showAndWait();
    }
}
