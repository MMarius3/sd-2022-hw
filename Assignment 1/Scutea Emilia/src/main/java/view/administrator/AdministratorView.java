package view.administrator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdministratorView {
    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private Button crudEmployee;
    private Button generateReports;
    private Button backBtn;

    public AdministratorView(){
        mainPage = new GridPane();
        scene = new Scene(mainPage, 400, 600);
        crudEmployee = new Button("CRUD on employee information");
        generateReports = new Button("Generate reports");
        backBtn = new Button("Back");
        setUpView();
    }

    private void setUpView(){
        mainPage.prefHeight(400.0);
        mainPage.prefWidth(600.0);
        crudEmployee.setLayoutY(125);
        crudEmployee.setLayoutY(54);
        generateReports.setLayoutX(125);
        generateReports.setLayoutY(110);
        backBtn.setLayoutX(125);
        backBtn.setLayoutY(188);

        mainPage.add(crudEmployee, 3, 1);
        mainPage.add(generateReports, 3, 3);
        mainPage.add(backBtn, 3, 5);

    }

    public void setScene(Scene scene){
        this.primaryStage.setScene(scene);
    }

    public Scene getScene(){
        return scene;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public Button getCrudEmployee() {
        return crudEmployee;
    }

    public Button getGenerateReports() {
        return generateReports;
    }

    public Button getBackBtn() {
        return backBtn;
    }
}
