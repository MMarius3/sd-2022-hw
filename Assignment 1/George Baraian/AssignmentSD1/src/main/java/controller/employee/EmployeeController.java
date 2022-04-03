package controller.employee;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import services.client.ClientService;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final ClientService clientService;

    public EmployeeController(EmployeeView employeeView, ClientService clientService) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        employeeView.setCreateClientButtonListener(new CreateClientButtonListener());
        employeeView.setViewClientsButtonListener(new ViewClientsButtonListener());
    }


    private class CreateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long clientId = Long.valueOf(employeeView.getId());
            String clientName = employeeView.getName();
            String clientAddress = employeeView.getAddress();
            Long clientPersonalNumericalCode = Long.valueOf(employeeView.getPersonalNumericalCode());
            Client client1 = new ClientBuilder().setId(clientId).setName(clientName)
                    .setAddress(clientAddress).setPersonalNumericalCode(clientPersonalNumericalCode).build();

            Notification<Boolean> createClientAccountNotification = clientService.save(client1);

            if(createClientAccountNotification.hasErrors()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(),createClientAccountNotification.getFormattedErrors());
            }else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Client created successfully!");
            }

        }
    }

    private class ViewClientsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> all = clientService.findAll();
            employeeView.createResultTable(all);
        }
    }
}
