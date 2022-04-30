package view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessageView {

    public void display(String message){
        Stage window = new Stage();
        window.setTitle("Message");

        Text text = new Text(message);



        window.setScene(new Scene(new VBox(text), 280, 100));
        window.setResizable(false);
        window.showAndWait();
    }
}
