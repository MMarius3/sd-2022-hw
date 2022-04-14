package view;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Client;
import model.User;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class AdminView extends Pane {
    private Button show = new Button("Show employees");
    private Button create = new Button("Create employee");
    private Button update = new Button("Update employee");
    private Button delete = new Button("Delete employee");
    private Button generate = new Button("Generate reports");
    private TableView<User> users;


    public AdminView(){
        this.setPrefSize(800,600);
        this.initializeFields();
    }

    public void initializeFields(){

        show.setLayoutX(30);
        show.setLayoutY(50);
        show.setFont(Font.font("Times New Roman",14));
        show.setStyle("-fx-base: cyan;");


        create.setLayoutX(30);
        create.setLayoutY(100);
        create.setFont(Font.font("Times New Roman",14));
        create.setStyle("-fx-base: cyan;");


        update.setLayoutX(30);
        update.setLayoutY(150);
        update.setFont(Font.font("Times New Roman",14));
        update.setStyle("-fx-base: cyan;");


        delete.setLayoutX(30);
        delete.setLayoutY(200);
        delete.setFont(Font.font("Times New Roman",14));
        delete.setStyle("-fx-base: cyan;");


        generate.setLayoutX(30);
        generate.setLayoutY(250);
        generate.setFont(Font.font("Times New Roman",14));
        generate.setStyle("-fx-base: cyan;");

        users = new TableView<>();
        users.setLayoutX(200);
        users.setLayoutY(50);

        User user =  new User();
        for(Field field:user.getClass().getDeclaredFields()){
            TableColumn col = new TableColumn(field.getName());
            col.setCellValueFactory(new PropertyValueFactory<Parameter,String>(field.getName()));
            users.getColumns().add(col);
        }


        this.getChildren().addAll(users,show,create,update,delete,generate);

    }

    public Button getShow() {
        return show;
    }

    public void setShow(Button show) {
        this.show = show;
    }

    public Button getCreate() {
        return create;
    }

    public void setCreate(Button create) {
        this.create = create;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public Button getGenerate() {
        return generate;
    }

    public TableView<User> getUsers() {
        return users;
    }

    public void setUsers(TableView<User> users) {
        this.users = users;
    }

    public void setGenerate(Button generate) {
        this.generate = generate;
    }
}
