package View;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class AdminView {

    private final GridPane mainGrid=new GridPane();
    private BorderPane mainPane=new BorderPane();
    private final Scene mainScene=new Scene(mainPane,930,600);
    private VBox vbox = new VBox();
    private final Text employeeIdMsg = new Text("Employee ID : ");
    private final Text usernameMsg = new Text("Username : ");
    private Text usernameMsg2 = new Text("Username : ");
    private final Text passowrdMsg = new Text("Password : ");
    private TextField employeeId = new TextField();
    private TextField username = new TextField();
    private TextField username2 = new TextField();
    private PasswordField password = new PasswordField();
    private Button addEmployee = new Button("Add Employee");
    private Button updateEmployee = new Button("Update Employee");
    private Button deleteEmployee = new Button("Delete Employee");
    private Button viewEmployees = new Button("View Employees");
    private Button generateReport = new Button("Generate Report");
    private Button logOut=new Button("LogOut");
    private Text errorMsg = new Text();
    private Text dateFromMsg = new Text("Date from : ");
    private Text dateToMsg = new Text("Date to : ");
    private TextField dateFrom = new TextField();
    private TextField dateTo = new TextField();

    public AdminView(){
        mainPane.setLeft(mainGrid);
        mainPane.setCenter(vbox);
        mainGrid.setHgap(5);
        mainGrid.setVgap(5);
        mainGrid.setPadding(new Insets(30,30,30,30));
        mainGrid.add(employeeIdMsg,2,2);
        mainGrid.add(employeeId,3,2);
        mainGrid.add(usernameMsg,2,3);
        mainGrid.add(username,3,3);
        mainGrid.add(passowrdMsg,2,4);
        mainGrid.add(password,3,4);
        addEmployee.setMaxSize(200,200);
        updateEmployee.setMaxSize(200,200);
        deleteEmployee.setMaxSize(200,200);
        viewEmployees.setMaxSize(200,200);
        generateReport.setMaxSize(200,200);
        logOut.setMaxSize(200,200);
        mainGrid.add(addEmployee,3,5);
        mainGrid.add(updateEmployee,3,6);
        mainGrid.add(deleteEmployee,3,7);
        mainGrid.add(viewEmployees,3,8);
        mainGrid.add(generateReport,3,15);
        mainGrid.add(usernameMsg2,2,12);
        mainGrid.add(username2,3,12);
        mainGrid.add(dateFromMsg,2,13);
        mainGrid.add(dateToMsg,2,14);
        mainGrid.add(dateFrom,3,13);
        mainGrid.add(dateTo,3,14);
        mainGrid.add(logOut,3,16);
        mainGrid.add(errorMsg,5,14,5,20);

    }

    public TableView createTable(ObservableList<?> objects){

        TableView<Type> tableView=new TableView<>();
        if(!objects.isEmpty()) {
            tableView.setItems((ObservableList<Type>) objects);
            for (Field field : objects.get(0).getClass().getDeclaredFields()) {
                field.setAccessible(true);
                TableColumn<Type,String> column=new TableColumn(field.getName());
                column.setCellValueFactory(new PropertyValueFactory(field.getName()));
                tableView.getColumns().add(column);
            }
        }
        return tableView;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public Button getLogOut() {
        return logOut;
    }

    public TextField getEmployeeId() {
        return employeeId;
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public PasswordField getPassword() {
        return password;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public Button getAddEmployee() {
        return addEmployee;
    }

    public Button getUpdateEmployee() {
        return updateEmployee;
    }

    public Button getDeleteEmployee() {
        return deleteEmployee;
    }

    public Button getViewEmployees() {
        return viewEmployees;
    }

    public Button getGenerateReport() {
        return generateReport;
    }

    public Text getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Text errorMsg) {
        this.errorMsg = errorMsg;
    }

    public VBox getVbox() {
        return vbox;
    }

    public TextField getDateFrom() { return dateFrom; }

    public TextField getDateTo() { return dateTo; }

    public TextField getUsername2() { return username2; }
}
