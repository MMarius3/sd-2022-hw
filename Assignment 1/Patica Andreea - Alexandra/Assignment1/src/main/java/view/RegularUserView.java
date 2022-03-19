package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegularUserView {

    ChoiceBox<String> choiceBox;
    private Text idFilterText;
    private Text nameFilterText;
    private TextField idTextField;
    private TextField nameTextField;
    private Button searchButton;
    private GridPane gridPane;
    private ScrollPane scrollPane;
    private HBox hBox;
    private static UserController controller;

    public void display(Stage window){
        window.setTitle("Regular User");

        initializeFields();
        initializeSearchButton();
        initializeGridPane();
        initializeHbox();
        //initializeChoiceBoxAction();
        //HBox hbox = new HBox(choiceBox);


        Scene scene = new Scene(hBox, 500, 500);
        window.setScene(scene);
    }

    private void initializeFields(){
        idFilterText = new Text("Filter by id");
        nameFilterText = new Text("Filter by name");
        idTextField = new TextField();
        nameTextField = new TextField();
        searchButton = new Button("Search");
        scrollPane = new ScrollPane();
    }

    private void initializeSearchButton(){
        searchButton.setOnAction(controller.handleSearchButtonListener());
    }

    private void initializeGridPane(){
        gridPane = new GridPane();
        gridPane.setPadding( new Insets(20, 20, 20, 20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.getChildren().addAll(idFilterText, nameFilterText, idTextField, nameTextField, searchButton);

        GridPane.setConstraints(idFilterText, 0, 1);
        GridPane.setConstraints(idTextField, 1, 1);
        GridPane.setConstraints(nameFilterText, 0, 2);
        GridPane.setConstraints(nameTextField, 1, 2);
        GridPane.setConstraints(searchButton, 1, 3);
    }

    private void initializeHbox(){
        hBox = new HBox();
        hBox.setPadding(new Insets(20, 20, 20, 20));

        hBox.getChildren().addAll(gridPane, scrollPane);
    }

    private void initializeChoiceBox(){
        choiceBox = new ChoiceBox<>();
        //controller.initializeChoiceBox(choiceBox);      //TODO

        choiceBox.getItems().add("Choice 1");
        choiceBox.getItems().add("Choice 2");
        choiceBox.getItems().add("Choice 3");
    }

    private void initializeChoiceBoxAction(){
        //controller.setRegularUserView(this);
        //choiceBox.setOnAction(controller.handleChoiceBoxListener());
    }

    public ChoiceBox<String> getChoiceBox() {
        return choiceBox;
    }

    public static void setController(UserController cntrl){
        controller = cntrl;
    }

    public Text getNameFilterText() {
        return nameFilterText;
    }

    public void setNameFilterText(Text nameFilterText) {
        this.nameFilterText = nameFilterText;
    }

    public TextField getIdTextField() {
        return idTextField;
    }

    public void setIdTextField(TextField idTextField) {
        this.idTextField = idTextField;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }
}
