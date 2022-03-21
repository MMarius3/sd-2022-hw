package controller;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import service.user.UserService;
import view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

public class AdminController {
    private final AdminView adminView;
    private final UserService userService;

    public AdminController(AdminView adminView, UserService userService){
        this.adminView = adminView;
        this.userService = userService;
        adminView.setBtnCreateListener(new AdminController.BtnCreateListener());
        adminView.setBtnUpdateListener(new AdminController.BtnUpdateListener());
        adminView.setBtnReadListener(new AdminController.BtnReadListener());
        adminView.setBtnDeleteListener(new AdminController.BtnDeleteListener());

    }

    public class BtnCreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = adminView.getTfUsername();
            String password = encodePassword(adminView.getTfPassword());

            User user = new UserBuilder()
                    .setUsername(name)
                    .setPassword(password)
                    .build();
            userService.create(user);
        }
    }

    public class BtnUpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = adminView.getTfUsername();
            String password = encodePassword(adminView.getTfPassword());
            Long id = Long.parseLong(adminView.getTfId());

            User user = new UserBuilder()
                    .setUsername(name)
                    .setPassword(password)
                    .build();
            userService.update(user,id);
        }
    }

    public class BtnReadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = adminView.getTfUsername();
            User user = userService.findByUsername(name);
            adminView.getLabel().setText("Username: " + user.getUsername() + "  " + "Password: "+ user.getPassword());
        }
    }

    public class BtnDeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.parseLong(adminView.getTfId());
            userService.remove(id);
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
