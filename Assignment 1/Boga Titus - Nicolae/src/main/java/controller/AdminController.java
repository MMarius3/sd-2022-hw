package controller;

import model.Client;
import model.User;
import service.employee.EmployeeService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class AdminController {

    private final AdminView adminView;
    private final EmployeeService employeeService;

    public AdminController(AdminView adminView,EmployeeService employeeService)
    {
        this.employeeService=employeeService;
        this.adminView = adminView;
        adminView.setUpdateButtonListener(new updateButtonListener());
        adminView.setShowButtonListener(new showButtonListener());
        adminView.setAddButtonListener(new addButtonListener());
        adminView.setDeleteAccountButtonListener(new deleteButtonListener());
        adminView.setReportButtonListener(new setButtonListener());
        List<User> users = employeeService.getUsers();
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for (User user:users
        ) {
            defaultListModel.addElement(user.getId().toString());
        }
        adminView.setEmployeesList(defaultListModel);

    }


    private class updateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username= adminView.getUsername();
            //Client client= new Client();
            User user= new User();
            user.setUsername(username);
            user.setId(adminView.getSelectedId());
            employeeService.updateEmail(user);
        }
    }


    private class showButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            User user = employeeService.findUserById(adminView.getSelectedId());
            adminView.setEmployeeEmail(user.getUsername());

        }
    }


    private class addButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    private class deleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    private class setButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
