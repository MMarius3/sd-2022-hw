package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DeleteAccountView extends JFrame{
    private JTextField idTextField;
    private JLabel idLabel;
    private JButton deleteAccountButton;

    public DeleteAccountView() {
        setSize(100, 100);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(idLabel);
        add(idTextField);
        add(deleteAccountButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        deleteAccountButton = new JButton("Delete account");
    }

    public Long getId(){ return Long.valueOf(idTextField.getText()); }

    public void deleteAccountButtonListener(ActionListener deleteAccountBtnListener){
        deleteAccountButton.addActionListener(deleteAccountBtnListener);
    }
}
