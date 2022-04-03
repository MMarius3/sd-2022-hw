package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UpdateUserView extends JFrame{
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel userNameLabel;
    private JTextField userNameTextField;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JButton updateUserButton;

    public UpdateUserView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(idLabel);
        add(idTextField);
        add(idLabel);
        add(idTextField);
        add(userNameLabel);
        add(userNameTextField);
        add(passwordLabel);
        add(passwordTextField);
        add(updateUserButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        userNameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        userNameTextField = new JTextField();
        passwordTextField = new JTextField();
        updateUserButton = new JButton("Update user");
    }

    public Long getId(){ return Long.valueOf(idTextField.getText()); }

    public String getUserNameTextField(){ return userNameTextField.getText(); }

    public String getPasswordTextField(){ return passwordTextField.getText(); }

    public void updateUserButtonListener(ActionListener updateUserBtnListener){
        updateUserButton.addActionListener(updateUserBtnListener);
    }
}
