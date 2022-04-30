package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class NewEmployeeView extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submit;

    public NewEmployeeView() {
        setSize(600, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initializeFields() {
        submit = new JButton("Submit");
        username = new JTextField();
        password = new JTextField();
        add(new JLabel("Username"));
        add(username);
        add(new JLabel("Password"));
        add(password);
        add(submit);
    }

    public void submitBtnListener(ActionListener btnListener) {
        submit.addActionListener(btnListener);
    }

    public String getUsername(){ return username.getText();}
    public String getPassword(){ return password.getText();}

}
