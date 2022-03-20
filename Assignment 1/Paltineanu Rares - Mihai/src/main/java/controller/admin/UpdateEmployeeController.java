package controller.admin;

import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import service.user.UserService;
import view.admin.ActionEmployeeView;
import view.admin.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class UpdateEmployeeController {
    private final ActionEmployeeView updateEmployeeView;
    private final AdminView adminView;
    private final UserValidator userValidator;
    private final UserService userService;
    private final Long id;

    public UpdateEmployeeController(ActionEmployeeView updateEmployeeView, AdminView adminView, UserValidator userValidator, UserService userService, Long id) {
        this.updateEmployeeView = updateEmployeeView;
        this.adminView = adminView;
        this.userValidator = userValidator;
        this.userService = userService;
        this.id = id;

        initializeFields();
        initializeButtonListener();
    }

    private void initializeButtonListener() {
        this.updateEmployeeView.setCancelButtonListener(new CancelButtonListener());
        this.updateEmployeeView.setAddEmployeeButtonListener(new UpdateButtonListener());
    }

    private void initializeFields() {
        User user = userService.findById(id);

        updateEmployeeView.getUsernameField().setText(user.getUsername());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateEmployeeView.setVisible(false);
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = updateEmployeeView.getUsernameField().getText();
            String password = new String(updateEmployeeView.getPasswordField().getPassword());

            User user = userService.findById(id);
            boolean verifyUsernameUniqueness = !user.getUsername().equals(username);
            userValidator.validate(username, password, verifyUsernameUniqueness);

            List<String> errors = userValidator.getErrors();

            if (errors.isEmpty()) {
                User newUser = new UserBuilder()
                        .setUsername(username)
                        .setPassword(encodePassword(password))
                        .build();
                boolean flag = userService.update(id, user);

                if (flag) {
                    JOptionPane.showMessageDialog(updateEmployeeView.getContentPane(), "Update successful");
                    updateEmployeeView.setVisible(false);
                    userService.update(id, newUser);
                    adminView.getViewEmployeesButton().doClick();

                } else {
                    JOptionPane.showMessageDialog(updateEmployeeView.getContentPane(), "An error occurred while trying to " +
                            "update user with id " + id);
                }
            } else {
                JOptionPane.showMessageDialog(updateEmployeeView.getContentPane(), userValidator.getFormattedErrors());
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
