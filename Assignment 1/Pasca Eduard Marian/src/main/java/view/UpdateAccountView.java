package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Date;


public class UpdateAccountView extends JFrame{
    private  JLabel idLabel;
    private JLabel typeLabel;
    private JLabel amountLabel;
    private JTextField idTextField;
    private JTextField typeTextField;
    private JTextField amountTextField;
    private JButton updateAccountButton;

    public UpdateAccountView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(idLabel);
        add(idTextField);
        add(typeLabel);
        add(typeTextField);
        add(amountLabel);
        add(amountTextField);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        typeLabel = new JLabel("Type:");
        amountLabel = new JLabel("Amount:");
        typeTextField = new JTextField();
        amountTextField = new JTextField();
        updateAccountButton = new JButton("Update account");
    }
    public Long getId(){ return Long.valueOf(idTextField.getText()); }

    public String getTypeTextField(){
        return typeTextField.getText();
    }

    public long getAmountTextField(){
        return Long.valueOf(amountTextField.getText());
    }

    public void updateAccountButtonListener(ActionListener updateAccountBtnListener){
        updateAccountButton.addActionListener(updateAccountBtnListener);
    }
}
