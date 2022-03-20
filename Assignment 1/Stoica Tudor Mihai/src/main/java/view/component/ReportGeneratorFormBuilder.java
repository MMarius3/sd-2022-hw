package view.component;

import controller.UserController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportGeneratorFormBuilder extends ComponentsFetcher {

    private UserController userController;
    private JButton submitButton;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JTextField userName;

    public ReportGeneratorFormBuilder setUserController(UserController userController) {
        this.userController = userController;
        return this;
    }

    public ReportGeneratorFormBuilder setSubmitButton() {
        submitButton = new JButton("Submit");
        this.addComponent(submitButton);
        return this;
    }

    public ReportGeneratorFormBuilder setStartDateTextField() {
        startDateTextField = new JTextField("Start");
        this.addComponent(startDateTextField);
        return this;
    }

    public ReportGeneratorFormBuilder setEndDateTextField() {
        endDateTextField = new JTextField("End");
        this.addComponent(endDateTextField);
        return this;
    }

    public ReportGeneratorFormBuilder setUserName() {
        userName = new JTextField("username");
        this.addComponent(userName);
        return this;
    }

    public ReportGeneratorFormBuilder buildDefaultForm() {
        return this.setStartDateTextField()
                .setEndDateTextField()
                .setUserName()
                .setSubmitButton()
                .setSubmitButtonActionListener(new SubmitButtonListener());
    }

    public ReportGeneratorFormBuilder setSubmitButtonActionListener(ActionListener actionListener) {
        this.submitButton.addActionListener(actionListener);
        return this;
    }

    private class SubmitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String startDate = startDateTextField.getText();
            String endDate = endDateTextField.getText();
            String username = userName.getText();

            userController.generateReportPDF(username, startDate, endDate);
        }
    }
}
