package controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Account;
import model.ActivityUser;
import model.Client;
import model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import java.util.*;

public class AdminController {
    @FXML
    TextField idTF;

    @FXML
    TextField usernameTF;

    @FXML
    TextField passwordTF;

    @FXML
    DatePicker date;

    @FXML
    Button addB;

    @FXML
    Button deleteB;

    @FXML
    Button updateB;

    @FXML
    Button viewB;

    @FXML
    Button generateB;

    @FXML
    TextArea viewTA;

    @FXML
    TextArea generateTA;

    private String username,password;
    private Long id;
    private Date period;
    ConnectionStuff con = new ConnectionStuff();


    public void addButtonOnAction(javafx.event.ActionEvent event){
       try{
           username = usernameTF.getText();
           password = passwordTF.getText();

           con.getUserValidator().validate(username, password);
           final List<String> errors = con.getUserValidator().getErrors();
           if (errors.isEmpty()) {
               con.getAuthenticationService().register(username, password);

           } else {
               dialogue(con.getUserValidator().getFormattedErrors());
           }
       }
       catch(NumberFormatException ex){ // handle your exception
        System.out.println("Empty username and/or password" +
                "fields");
    }

    }

    public void deleteButtonOnAction(){
        try{
            username = usernameTF.getText();
            System.out.println("step0"+username);
            con.getAuthenticationService().removeByUsername(username);
        }
        catch(NumberFormatException ex){ // handle your exception
            System.out.println("Empty username " +
                    "fields");
        }
    }

    public void updateButtonOnAction(){

        try{
            username = usernameTF.getText();
            password = passwordTF.getText();
            id = Long.parseLong(idTF.getText());

            con.getUserValidator().validateNotUnique(username, password);
            final List<String> errors = con.getUserValidator().getErrors();
            if (errors.isEmpty()) {
                con.getAuthenticationService().update(id,username, password);

            } else {
                dialogue(con.getUserValidator().getFormattedErrors());
            }
        }
        catch(NumberFormatException ex){ // handle your exception
            System.out.println("Empty username and/or password" +
                    "fields");
        }

    }

    public void viewButtonOnAction(){
        List<User> users = con.getAuthenticationService().view();
        StringBuilder sb = new StringBuilder();
        if(!users.isEmpty()){
            for(User c : users)
                sb.append(String.format("User username %s and password %s.\n",c.getUsername(),c.getPassword()));
        }
        viewTA.setText(sb.toString());

    }

    private void getDate(){
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        period = Date.from(instant);
    }
    public void generateButtonOnAction(){
      try {
          id = Long.parseLong(idTF.getText());
      }catch(NumberFormatException ex){ // handle your exception
          System.out.println("Empty date or id");
      }
            getDate();
          StringBuilder sb = new StringBuilder();

          List<ActivityUser> activities = con.activityUserRepository.findByPeriodAndUsername(period,username);

          for(ActivityUser au : activities){
              sb.append(au.getActivity());
          }
          generateTA.setText(sb.toString());
    }

    private void dialogue(String information){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("error");
        alert.setHeaderText(null);
        alert.setContentText(information);
        alert.showAndWait();
    }

}
