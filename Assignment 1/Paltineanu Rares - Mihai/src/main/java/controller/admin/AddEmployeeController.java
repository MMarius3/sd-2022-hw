package controller.admin;

import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import service.user.UserService;
import view.admin.ActionEmployeeView;
import view.admin.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AddEmployeeController {
    private final AdminView adminView;
    private final ActionEmployeeView addEmployeeView;
    private final UserService userService;
    private final UserValidator userValidator;
    private final RightsRolesRepository rightsRolesRepository;
    public AddEmployeeController(AdminView adminView,
                                 ActionEmployeeView addEmployeeView,
                                 UserService userService,
                                 UserValidator userValidator, RightsRolesRepository rightsRolesRepository) {
        this.adminView = adminView;
        this.addEmployeeView = addEmployeeView;
        this.userService = userService;
        this.userValidator = userValidator;
        this.rightsRolesRepository = rightsRolesRepository;

        initializeButtonListener();
    }

    private void initializeButtonListener() {
        addEmployeeView.setCancelButtonListener(new CancelButtonListener());
        addEmployeeView.setAddEmployeeButtonListener(new AddButtonListener());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addEmployeeView.setVisible(false);
        }
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = addEmployeeView.getUsername();
            String password = addEmployeeView.getPassword();

            userValidator.validate(username, password, true);

            final List<String> errors = userValidator.getErrors();

            if(errors.isEmpty()) {
                User user = new UserBuilder()
                        .setUsername(username)
                        .setPassword(encodePassword(password))
                        .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE)))
                        .build();

                boolean flag = userService.save(user);

                if(flag) {
                    JOptionPane.showMessageDialog(addEmployeeView.getContentPane(), "Add successful");
                    addEmployeeView.setVisible(false);
                    adminView.clickViewEmployeesButton();
                } else {
                    JOptionPane.showMessageDialog(addEmployeeView.getContentPane(), "An error occurred while trying" +
                            " to add an employee");
                }
            } else {
                JOptionPane.showMessageDialog(addEmployeeView.getContentPane(), userValidator.getFormattedErrors());
            }
        }

        private String encodePassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                return hexString.toString();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
