package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DeleteUserView extends JFrame{
    private JTextField idTextField;
    private JLabel idLabel;
    private JButton deleteUserButton;

    public DeleteUserView() {
        setSize(100, 100);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(idLabel);
        add(idTextField);
        add(deleteUserButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        deleteUserButton = new JButton("Delete account");
    }

    public Long getId(){ return Long.valueOf(idTextField.getText()); }

    public void deleteAccountButtonListener(ActionListener deleteUserBtnListener){
        deleteUserButton.addActionListener(deleteUserBtnListener);
    }
}