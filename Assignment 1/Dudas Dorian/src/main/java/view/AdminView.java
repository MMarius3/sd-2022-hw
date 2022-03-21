package view;

import model.User;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {
    private JButton btnEmployeeInfoView;
    private JButton btnClientView;
    private JButton btnBack;

    public AdminView(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(btnEmployeeInfoView);
        add(btnClientView);
        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        btnEmployeeInfoView = new JButton("Manage Employees");
        btnClientView = new JButton("Manage Clients");
        btnBack = new JButton("Back button");
    }

    public void addClientManagerButtonListener(ActionListener loginButtonListener) {
        btnClientView.addActionListener(loginButtonListener);
    }

    public void addEmployeeManagerButtonListener(ActionListener loginButtonListener) {
        btnEmployeeInfoView.addActionListener(loginButtonListener);
    }

    public void addBackButtonListener(ActionListener loginButtonListener) {
        btnBack.addActionListener(loginButtonListener);
    }
}
