package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.Client;
import model.Right;
import model.Role;
import model.User;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.MainUI;
import view.UserView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private UserView userView;
    private User user;
    private final ClientService clientService;
    private final UserService userService;
    private final ClientController clientController;
    private final EmployeeController employeeController;

    public UserController(UserService userService, ClientService clientService, ClientController clientController, EmployeeController employeeController){
        this.userService = userService;
        this.clientService = clientService;
        this.clientController = clientController;
        this.employeeController = employeeController;
    }

    public void startController(User user){
        this.user = user;
        userView = new UserView();
        UserView.setController(this);
        //initializeChoiceBox(regularUserView.getChoiceBox());
        userView.display(MainUI.getWindow(), user.getRoles().get(0).getRole());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRegularUserView(UserView regularUserView){
        this.userView = regularUserView;
    }


    public EventHandler<ActionEvent> handleSearchButtonListener(){
        return e -> {
            Integer id;
            if (!userView.getIdTextField().getText().equals("")){
                id = Integer.valueOf(userView.getIdTextField().getText());
            }
            else{
                id = null;
            }
            String name = userView.getNameTextField().getText();


            boolean addedUsers = false;
            boolean addedClients = false;
            for(Role role : user.getRoles()){
                //for(Right right1 : role.getRights()){
                    //if (!addedUsers &&  right1.getRight().equals(Constants.Rights.CREATE_USER)){
                    if (role.getRole().toString().equals(Constants.Roles.ADMINISTRATOR)){
                        addedUsers = true;
                        if (id == null && name.equals("")){
                            addUserList(userService.findAll());
                        }
                        else if (id != null){
                            List<User> users = new ArrayList<>();
                            users.add(userService.findById(id.longValue()));
                            addUserList(users);
                        }
                        else {
                            List<User> users = new ArrayList<>();
                            users.add(userService.findByName(name));
                            addUserList(users);
                        }
                    }
                    //if (!addedClients && right1.getRight().equals(Constants.Rights.CREATE_CLIENT)){
                    if (role.getRole().toString().equals(Constants.Roles.USER)){
                        addedClients = true;
                        if (id == null && name.equals("")){
                            addClientList(clientService.findAll());
                        }
                        else if (id != null){
                            List<Client> clients = new ArrayList<>();
                            clients.add(clientService.findById(id.longValue()));
                            addClientList(clients);
                        }
                        else {
                            List<Client> clients = new ArrayList<>();
                            clients.add(clientService.findByName(name));
                            addClientList(clients);
                        }
                    }
               // }
            }
        };
    }

    public EventHandler<ActionEvent> handleAddButtonListener(){
        return e ->{
            if (user.getRoles().get(0).getRole().equals(Constants.Roles.USER)){
                clientController.startController(null);
            }
            else{
                employeeController.startController(null);
            }
        };
    }

    public EventHandler<ActionEvent> handleLogoutButtonListener(){
        return e ->{
            user = null;
            MainUI.setLoginScene();
        };
    }

    private void addUserList(List<User> users){
        List<Button> buttons = new ArrayList<>();
        for (User user1 : users){
            Button button = new Button(user1.getId() + " " + user1.getUsername());
            button.setOnAction(e -> {
                userButtonListener(user1);
            });
            buttons.add(button);
        }
        userView.refreshScrollPane(buttons);
    }

    private void addClientList(List<Client> clients){
        List<Button> buttons = new ArrayList<>();
        for (Client client : clients){
            Button button = new Button(client.getId() + " " + client.getName());
            button.setOnAction(e -> {
                clientButtonListener(client);
            });
            buttons.add(button);
        }
        userView.refreshScrollPane(buttons);
    }

    private void userButtonListener(User user){
        employeeController.startController(user);
    }

    private void clientButtonListener(Client client){
        clientController.startController(client);
    }
}
