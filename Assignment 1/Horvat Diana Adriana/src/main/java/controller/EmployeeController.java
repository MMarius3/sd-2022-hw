package controller;

import model.Client;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import respository.client.ClientRepository;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import view.RowFilterUtil;
import view.employee.EmployeeAddClientView;
import view.employee.EmployeeIndexView;
import view.employee.EmployeeUpdateClientView;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {

    private final EmployeeIndexView employeeIndexView;
    private final EmployeeAddClientView employeeAddClientView;
    private final EmployeeUpdateClientView employeeUpdateClientView;

    private final ClientValidator clientValidator;
    private final ClientService clientService;

    public EmployeeController(ClientService clientService, ClientValidator clientValidator,
                              EmployeeIndexView employeeIndexView, EmployeeAddClientView employeeAddClientView,
                              EmployeeUpdateClientView employeeUpdateClientView){
        this.clientService = clientService;
        this.clientValidator = clientValidator;
        this.employeeIndexView = employeeIndexView;
        this.employeeAddClientView = employeeAddClientView;
        this.employeeUpdateClientView = employeeUpdateClientView;

        this.employeeIndexView.addClientViewBtnListener(new CreateClientViewBtnListener());
        this.employeeAddClientView.addClientBtnListener(new AddClientBtnListener());
        this.employeeIndexView.addUpdateClientBtnListener(new UpdateClientViewBtnListener());
        this.employeeUpdateClientView.addSearchClientBtnListener(new SearchClientBtnListener());
        this.employeeUpdateClientView.addEditClientBtnListener(new EditClientBtnListener());
    }

    private class CreateClientViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeAddClientView.setVisible(true);
        }
    }

    private class AddClientBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeAddClientView.getName();
            String idCardNr = employeeAddClientView.getIdCardNr();
            String PNC = employeeAddClientView.getPNC();
            String address = employeeAddClientView.getAddress();
            String email = employeeAddClientView.getEmail();

            clientValidator.validate(name, idCardNr, PNC, address, email);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                if(clientService.addClient(name, idCardNr, PNC, address, email)){
                    JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), "Client added successfully");
                }else{
                    JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), "Something went wrong");
                }

            } else {
                JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateClientViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeUpdateClientView.setVisible(true);
        }
    }

    private class SearchClientBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String PNCStr = employeeUpdateClientView.getTfPNC();

            if(clientValidator.validatePNC(PNCStr) == null){
                JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Personal numerical code is not valid");
            }else{
                Long PNC = Long.parseLong(PNCStr);
                Client client = clientService.findByPNC(PNC);

                if(client == null){
                    JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Client not found");

                }else{
                    employeeUpdateClientView.setClientData(client.getName(), client.getIdCardNr(), client.getAddress(), client.getEmail());
                }
            }

        }
    }

    private class EditClientBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String PNCStr = employeeUpdateClientView.getTfPNC();
            String idCardNrStr = employeeUpdateClientView.getTfIdCardNr();
            String address = employeeUpdateClientView.getTfAddress();
            String email = employeeAddClientView.getEmail();
            String name = employeeAddClientView.getName();

            clientValidator.validateUpdate(idCardNrStr, PNCStr, email);
            final List<String> errors = clientValidator.getErrors();

            if (errors.isEmpty()){
                Long PNC = Long.parseLong(PNCStr);
                Long idCardNr = Long.parseLong(idCardNrStr);

                Client client = new ClientBuilder()
                        .setName(name)
                        .setIdCardNr(idCardNr)
                        .setPNC(PNC)
                        .setAddress(address)
                        .setEmail(email)
                        .build();

                if(clientService.updateClient(client)){
                    JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Client updated");
                }else{
                    JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Something went wrong");
                }
            }else{
                JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), clientValidator.getFormattedErrors());

            }
        }
    }



}
