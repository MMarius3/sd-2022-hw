import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

public class MainUI extends Application {
    private Stage window;
    private LoginView loginView;


    public static void launchGUI() {
        launch();
    }

    @Override
    public void start(Stage primaryStage)  {
        window = primaryStage;
        window.setTitle("CNC flame cutting machine simulator");

        loginView = new LoginView();
        window.setScene(loginView.display(window));
        window.show();

    }


}
