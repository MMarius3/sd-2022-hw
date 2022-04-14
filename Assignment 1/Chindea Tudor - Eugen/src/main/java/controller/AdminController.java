package controller;

import model.User;
import model.builder.ClientBuilder;
import model.validator.UserValidator;
import repository.security.RolesRepository;
import repository.user.UserRepository;
import service.AuthenticationService;
import view.AdminView;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final AuthenticationService authenticationService;
    public AdminController(AdminView adminView, UserValidator userValidator, UserRepository userRepository, RolesRepository rolesRepository, AuthenticationService authenticationService){
        this.adminView = adminView;
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.authenticationService = authenticationService;
        this.adminView.addCreateEmployeeButonListener(new AddEmployeeButtonListener());
        this.adminView.addUpdateEmployeeButonListener(new UpdateEmployeeButtonListener());
        this.adminView.addDeleteEmployeeButonListener(new DeleteEmployeeButtonListener());
        this.adminView.addViewEmployeeButonListener(new ViewEmployeeButtonListener());
    }
    private class AddEmployeeButtonListener implements ActionListener {
        @Override
        public  void actionPerformed(ActionEvent e){
            String username = adminView.getEmpUsername();
            String password = adminView.getEmpPassword();

            userValidator.validate(username,password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password);
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }
    private class UpdateEmployeeButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Long id = Long.valueOf(adminView.getEmpId());
            String username = adminView.getEmpUsername();
            userRepository.updateEmployeeUsername(id,username);
        }
    }
    private class DeleteEmployeeButtonListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String id = adminView.getEmpId();
            userRepository.removeById(Long.valueOf(id));
        }
    }
    private class ViewEmployeeButtonListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(adminView.getEmpId());
            User user = userRepository.findById(id);
            adminView.setEmpUsername(user.getUsername());
        }
    }
}
