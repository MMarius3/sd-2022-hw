package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;


public class AddAccountView extends JFrame{
    private JLabel typeLabel;
    private JLabel amountLabel;
    private JTextField typeTextField;
    private JTextField amountTextField;
    private JButton addAccountButton;

    public AddAccountView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(typeLabel);
        add(typeTextField);
        add(amountLabel);
        add(amountTextField);
        add(addAccountButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        typeLabel = new JLabel("Type:");
        amountLabel = new JLabel("Amount:");
        typeTextField = new JTextField();
        amountTextField = new JTextField();
        addAccountButton = new JButton("Add account");
    }

    public String getTypeTextField(){
        return typeTextField.getText();
    }

    public Long getAmountTextField(){
        return Long.valueOf(amountTextField.getText());
    }

    public Date getDateTextField(){
        return Date.valueOf(LocalDate.now());
    }

    public void addAccountButtonListener(ActionListener addAccountBtnListener){
        addAccountButton.addActionListener(addAccountBtnListener);
    }
}
