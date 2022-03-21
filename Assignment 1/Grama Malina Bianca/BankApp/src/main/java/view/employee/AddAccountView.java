package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AddAccountView extends JFrame {

    private JTextField clientId;
    private JTextField accountType;
    private JTextField accountAmount;
    private JButton generateBtn;

    public AddAccountView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(clientId);
        add(accountAmount);
        add(accountType);
        add(generateBtn);
    }

    private void initializeFields() {
        clientId = new JTextField("ID of the client for which to add an account");
        accountType = new JTextField("Account Type");
        accountAmount = new JTextField("Account Amount");
        generateBtn = new JButton("Generate Account");
    }

    public String getClientId() {
        return clientId.getText();
    }

    public String getAccountType() {
        return accountType.getText();
    }

    public String getAccountAmount() {
        return accountAmount.getText();
    }

    public void setAddAccountBtnListener(ActionListener addAccountBtnListener) {
        generateBtn.addActionListener(addAccountBtnListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}
