package view.admin;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton createEmployeeButton;
    private JButton deleteEmployeeButton;
    private JButton viewEmployeesButton;

    public AdminView() throws HeadlessException{
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(createEmployeeButton);
        add(deleteEmployeeButton);
        add(viewEmployeesButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        createEmployeeButton = new JButton("Create an employee");
        deleteEmployeeButton = new JButton("Delete employee");
        viewEmployeesButton = new JButton("View employees");
    }

    public String getUsername(){
        return tfUsername.getText();
    }

    public String getPassword(){
        return tfPassword.getText();
    }

    public void setCreateEmployeeButtonListener(ActionListener createEmployeeButtonListener){
        createEmployeeButton.addActionListener(createEmployeeButtonListener);
    }

    public void setDeleteEmployeeButtonListener(ActionListener deleteEmployeeButtonListener){
        deleteEmployeeButton.addActionListener(deleteEmployeeButtonListener);
    }

    public void setViewEmployeesButtonListener(ActionListener viewEmployeesButtonListener){
        viewEmployeesButton.addActionListener(viewEmployeesButtonListener);
    }

    public void createResultTable(List<User> all) {
        JFrame f = new JFrame();
        String data[][] = new String[all.size()][2];
        for (int i = 0; i < all.size(); i++) {
            String id = String.valueOf(i+1);
            String username = all.get(i).getUsername();
            data[i][0] = id;
            data[i][1] = username;
        }
        String column[] = {"id", "username"};

        JTable jT = new JTable(data, column);
        jT.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(jT);
        f.add(sp);
        f.setSize(300, 400);
        f.setVisible(true);
    }

        public void setVisible(){
        this.setVisible(true);
    }


}
