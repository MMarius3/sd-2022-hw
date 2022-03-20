package View;

import Model.Client;
import Model.User;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class AdminView extends Pane {
    private Button view;
    private Button add;
    private Button delete;
    private Button update;
    private Button toUpdate;
    private TextField name;
    private TextField password;
    private TableView<User> userTableView;
    private DatePicker start;
    private DatePicker finish;
    private ComboBox<User> userComboBox;
    private Button generateReport;

    public AdminView(){
        initializeFields();
        setPrefSize(1000,720);
        view.setLayoutX(20);
        view.setLayoutY(20);
        add.setLayoutX(20);
        add.setLayoutY(60);
        delete.setLayoutX(20);
        delete.setLayoutY(100);
        update.setLayoutX(20);
        update.setLayoutY(140);
        name.setLayoutX(20);
        name.setLayoutY(180);
        password.setLayoutX(20);
        password.setLayoutY(220);
        start.setLayoutX(20);
        start.setLayoutY(260);
        finish.setLayoutX(20);
        finish.setLayoutY(300);
        userTableView.setLayoutX(360);
        userTableView.setLayoutY(20);
        userComboBox.setLayoutX(20);
        userComboBox.setLayoutY(340);
        toUpdate.setLayoutX(20);
        toUpdate.setLayoutY(380);
        generateReport.setLayoutX(20);
        generateReport.setLayoutY(420);
        for(Field field : User.class.getDeclaredFields()){
            if(field.getName() != "role") {
                TableColumn col = new TableColumn(field.getName());
                col.setCellValueFactory(new PropertyValueFactory<Parameter, String>(field.getName()));
                userTableView.getColumns().add(col);
            }
        }

        getChildren().addAll(add,view,update,delete,name,password,userTableView,start,finish,userComboBox,toUpdate,generateReport);
    }

    public Button getGenerateReport() {
        return generateReport;
    }

    public ComboBox<User> getUserComboBox() {
        return userComboBox;
    }

    public DatePicker getStart() {
        return start;
    }

    public DatePicker getFinish() {
        return finish;
    }

    public TableView<User> getUserTableView() {
        return userTableView;
    }

    public Button getView() {
        return view;
    }

    public Button getAdd() {
        return add;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getUpdate() {
        return update;
    }

    public Button getToUpdate() {
        return toUpdate;
    }

    public TextField getName() {
        return name;
    }

    public TextField getPassword() {
        return password;
    }

    private void initializeFields(){
        view = new Button("view employees");
        add = new Button("create employee");
        delete = new Button("delete");
        update = new Button("update");
        toUpdate = new Button("To update");
        generateReport = new Button("Generate report");

        name = new TextField();
        name.setPromptText("username");
        password = new TextField();
        password.setPromptText("password");

        userTableView = new TableView<>();
        start = new DatePicker();
        finish = new DatePicker();
        userComboBox = new ComboBox<>();

    }

}
