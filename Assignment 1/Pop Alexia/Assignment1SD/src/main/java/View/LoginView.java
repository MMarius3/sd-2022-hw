package View;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView {

    private GridPane mainPage=new GridPane();
    private Scene mainScene=new Scene(mainPage,400,300);
    private final Text usernameMsg=new Text("Username : ");
    private final Text passwordMsg=new Text("Password : ");
    private Text noSuchUserMsg = new Text();
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Button login = new Button("Login");
    private Stage primaryStage;


    public LoginView(){
        mainPage.setHgap(5);
        mainPage.setVgap(5);
        mainPage.setPadding(new Insets(30,30,30,30));
        mainPage.add(usernameMsg,7,5);
        mainPage.add(username,8,5);
        mainPage.add(passwordMsg,7,6);
        mainPage.add(password,8,6);
        mainPage.add(login,8,10);
        login.setMaxSize(200,200);
        mainPage.add(noSuchUserMsg,8,15);
    }

    public TextField getUsername(){
        return username;
    }
    public TextField getPassword(){
        return password;
    }

    public void changeView(Scene scene){
       this.primaryStage.setScene(scene);
    }


    public GridPane getMainPage() {
        return mainPage;
    }

    public void setMainPage(GridPane mainPage) {
        this.mainPage = mainPage;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public Button getLogin() {
        return login;
    }

    public void setLogin(Button login) {
        this.login = login;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Text getNoSuchUserMsg() {
        return noSuchUserMsg;
    }

    public void setNoSuchUserMsg(Text noSuchUserMsg) {
        this.noSuchUserMsg = noSuchUserMsg;
    }
}
