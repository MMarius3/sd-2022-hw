package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import view.EmployeeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final ClientRepositoryMySQL clientRepository;
    private final AccountRepositoryMySQL accountRepository;

    public EmployeeController(EmployeeView employeeView, ClientRepositoryMySQL clientRepository, AccountRepositoryMySQL accountRepository) {
        this.employeeView = employeeView;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;


        this.employeeView.addCreateButtonListener(new CreateButtonListener());
        this.employeeView.addUpdateButtonListener(new UpdateButtonListener());
        this.employeeView.addDeleteButtonListener(new DeleteButtonListener());
        this.employeeView.addViewButtonListener(new ViewButtonListener());
        this.employeeView.addACreateButtonListener(new ACreateButtonListener());
        this.employeeView.addADeleteButtonListener(new ADeleteButtonListener());
        this.employeeView.addAViewButtonListener(new AViewButtonListener());
        this.employeeView.setVisible(true);

    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Client user = new ClientBuilder()
                    .setName(employeeView.getName())
                    .setIdcardnumber(Long.parseLong(employeeView.getIdcardnumber()))
                    .setCnp(Long.parseLong(employeeView.getCnp()))
                    .setAddress(employeeView.getAddress())
                    .build();
            clientRepository.save(user);
        }
    }
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Client user = new ClientBuilder()
                    .setName(employeeView.getName())
                    .setIdcardnumber(Long.parseLong(employeeView.getIdcardnumber()))
                    .setCnp(Long.parseLong(employeeView.getCnp()))
                    .setAddress(employeeView.getAddress())
                    .build();
            clientRepository.updateByName(employeeView.getName(),user);
        }
    }
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            clientRepository.deleteByName(employeeView.getName());
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client c=clientRepository.findByName(employeeView.getName());
            employeeView.setName(c.getName());
            employeeView.setAddress(c.getAddress());
            employeeView.setCnp(c.getCnp());
            employeeView.setIdcardnumber(c.getIdcardnumber());
            employeeView.setId(c.getId());
        }
    }

    private class ACreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account user = new AccountBuilder()
                    .setIdnumber(Long.parseLong(employeeView.getaIdnumber()))
                    .setType(employeeView.getaType())
                    .setMoney(Long.parseLong(employeeView.getaMoney()))
                    .setDateOfCreation(employeeView.getaDateOfCreation())
                    .setClientID(Integer.parseInt(employeeView.getaClientID()))
                    .build();
            accountRepository.save(user);
        }
    }
    private class AViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account c=accountRepository.findByIdnumber(Long.parseLong(employeeView.getaIdnumber()));
            employeeView.setAdateOfCreation(c.getDateOfCreation());
            employeeView.setAType(c.getType());
            employeeView.setAMoney(c.getMoney());
            employeeView.setAIdnumber(c.getIdnumber());
            employeeView.setAClientId(c.getClientID());
        }
    }

    private class ADeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            accountRepository.deleteByIdnumber(Long.parseLong(employeeView.getaIdnumber()));
        }
    }


}
