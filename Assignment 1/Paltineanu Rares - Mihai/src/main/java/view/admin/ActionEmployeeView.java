package view.admin;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class ActionEmployeeView extends JFrame {
    private Container container;

    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton addEmployeeButton;
    private JButton cancelButton;

    public ActionEmployeeView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }


    private void initializeFrame() {
        setSize(280, 240);
        setResizable(false);
        setVisible(true);
        setTitle("Add employee");
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        addEmployeeButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
    }

    private void setBounds() {
        usernameLabel.setBounds(30, 30, 100, 20);
        passwordLabel.setBounds(30, 70, 100, 20);

        usernameField.setBounds(140, 30, 100, 20);
        passwordField.setBounds(140, 70, 100, 20);

        addEmployeeButton.setBounds(30, 120, 90, 30);
        cancelButton.setBounds(150, 120, 90, 30);
    }

    private void addComponentsToContainer() {
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(usernameField);
        container.add(passwordField);
        container.add(addEmployeeButton);
        container.add(cancelButton);
    }

    public void setAddEmployeeButtonListener(ActionListener actionListener) {
        this.addEmployeeButton.addActionListener(actionListener);
    }

    public void setCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }
}
