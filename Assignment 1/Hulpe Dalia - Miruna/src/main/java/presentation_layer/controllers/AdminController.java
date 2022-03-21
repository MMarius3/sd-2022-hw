package presentation_layer.controllers;

import bussiness_layer.builder.UserBuilder;
import bussiness_layer.models.Action;
import bussiness_layer.models.User;
import bussiness_layer.service.admin.AdminService;
import presentation_layer.view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;

    public AdminController(AdminView adminView, AdminService adminService) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.adminView.addViewEmployeeButtonListener(new AddViewEmployeeButtonListener());
        this.adminView.addNewEmployeeButtonListener(new AddNewEmployeeButtonListener());
        this.adminView.addUpdateEmployeeButtonListener(new AddUpdateEmployeeButtonListener());
        this.adminView.addDeleteEmployeeButtonListener(new AddDeleteEmployeeButtonListener());
        this.adminView.addGenerateReportsButtonListener(new AddGenerateReportsButtonListener());
        this.adminView.setVisible(false);
    }

    private class AddViewEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> customers = adminService.getAllEmployees();

            String[] columnNames = new String[] {"Username", "Password", "Id_Series", "Id_number", "Pnc", "Address"};
            String[][] data = new String[customers.size()][6];

            for (int i = 0; i< customers.size(); i++)
            {
                System.out.println(customers.get(i).getUsername() + " " + customers.get(i).getPassword() + " "+ customers.get(i).getId_series()+ " "+customers.get(i).getId_number().toString()+" "+ customers.get(i).getPnc()+" "+customers.get(i).getAddress());
                data[i][0] = customers.get(i).getUsername();
                data[i][1] = customers.get(i).getPassword();
                data[i][2] = customers.get(i).getId_series();
                data[i][3] = customers.get(i).getId_number().toString();
                data[i][4] = customers.get(i).getPnc();
                data[i][5] = customers.get(i).getAddress();
            }

            adminView.addViewEmployeeTable(data,columnNames);
        }
    }

    private class AddNewEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getTfUsername1Employee();
            String password = adminView.getTfPassword1Employee();
            String id_series = adminView.getTfId_Series1Employee();
            String id_number = adminView.getTfId_Number1Employee();
            String pnc = adminView.getTfPnc1Employee();
            String address = adminView.getTfAddress1Employee();

            User newEmployee = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setId_series(id_series)
                    .setId_number(Long.parseLong(id_number) )
                    .setPnc(pnc)
                    .setAddress(address)
                    .build();
            boolean verify =  adminService.addEmployee(newEmployee);
        }
    }

    private class AddUpdateEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String usernameInitial = adminView.getTfUsername1Employee();

            User initialUser = adminService.findByUsername(usernameInitial);

            String username = adminView.getTfUsername2Employee();
            if (username.equals(""))
            {
                username = initialUser.getUsername();
            }

            String password = initialUser.getPassword();

            String id_series = adminView.getTfId_Series2Employee();
            if(id_series.equals(""))
            {
                id_series = initialUser.getId_series();
            }

            String id_number = adminView.getTfId_Number2Employee();
            if (id_number.equals(""))
            {
                id_number = initialUser.getId_number().toString();
            }

            String pnc = adminView.getTfPnc2Employee();
            if (pnc.equals(""))
            {
                pnc = initialUser.getPnc();
            }

            String address = adminView.getTfAddress2Employee();

            if (address.equals(""))
            {
                address = initialUser.getAddress();
            }

            User updatedEmployee = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setId_series(id_series)
                    .setId_number(Long.parseLong(id_number) )
                    .setPnc(pnc)
                    .setAddress(address)
                    .build();

            boolean verify =  adminService.updateEmployee(usernameInitial, updatedEmployee);
        }
    }

    private class AddDeleteEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getTfUsername1Employee();
            boolean verify =  adminService.deleteEmployee(username);
        }
    }

    private class AddGenerateReportsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getTfUsername1Employee();

            //Due to lack of time, I wasn't able to read a date from the interface so here in this method a put the current date in order to test. The rest is implemented, just the reading of the date not
            List<Action> actions = adminService.getReport(new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),username);

            String[] columnNames = new String[] {"type", "date"};
            String[][] data = new String[actions.size()][2];

            for (int i = 0; i< actions.size(); i++)
            {
                System.out.println(actions.get(i).getType());
                data[i][0] = actions.get(i).getType();
                data[i][1] = actions.get(i).getDate_of_creation().toString();
            }

            adminView.addViewEmployeeTable(data,columnNames);
        }
    }

//    private class ViewClientButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            List<User> customers = employeeService.getAllCustomers();
//            for (int i = 0; i< customers.size(); i++)
//            {
//                System.out.println(customers.get(i).getUsername());
//            }
//        }
//    }

//  private class RegisterButtonListener implements ActionListener {
//    @Override
//    public void actionPerformed(ActionEvent e) {
//      String username = loginView.getUsername();
//      String password = loginView.getPassword();
//
//      userValidator.validate(username, password);
//      final List<String> errors = userValidator.getErrors();
//      if (errors.isEmpty()) {
//        authenticationService.register(username, password);
//      } else {
//        JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
//      }
}
