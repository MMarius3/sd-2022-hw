package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {

    private JList<String> jlEmployeesList;
    //private JTextField tfLoggedUser;

    private JTextField tfEmployeeEmail;
    private JLabel jlEmail;

    private JButton btnUpdate;
    private JButton btnShow;
    private JButton btnAddEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnGetReports;
    private JTextField tfReportData;


    public AdminView() throws HeadlessException {
        setSize(300, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(jlEmployeesList);
        add(jlEmail);
        add(tfEmployeeEmail);
        add(btnShow);
        add(btnUpdate);
        add(btnAddEmployee);
        add(btnDeleteEmployee);
        add(tfReportData);
        add(btnGetReports);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {


        btnGetReports = new JButton("Get Reports");

        tfReportData = new JTextField();

        jlEmail = new JLabel("Email:");

        jlEmployeesList = new JList<String>();
        btnUpdate = new JButton("Update Selected Employee");
        btnShow = new JButton("Show Selected Employee");
        btnAddEmployee = new JButton("Add New Employee");

        tfEmployeeEmail = new JTextField();

        btnDeleteEmployee = new JButton("Delete Selected Employee");

    }

    public void setVisibility(boolean visibility) {
        this.setVisible(visibility);
    }

    public void setUpdateButtonListener(ActionListener loginButtonListener) {
        btnUpdate.addActionListener(loginButtonListener);
    }

    public void setShowButtonListener(ActionListener registerButtonListener) {
        btnShow.addActionListener(registerButtonListener);
    }

    public void setAddButtonListener(ActionListener addButtonListener) {
        btnAddEmployee.addActionListener(addButtonListener);
    }


    public void setDeleteAccountButtonListener(ActionListener loginButtonListener) {
        btnDeleteEmployee.addActionListener(loginButtonListener);
    }

    public void setReportButtonListener(ActionListener loginButtonListener) {
        btnGetReports.addActionListener(loginButtonListener);
    }


    public void setEmployeesList(DefaultListModel<String> defaultListModel)
    {

        jlEmployeesList.setModel(defaultListModel);

    }

    public long getSelectedId()
    {
        return Integer.parseInt(jlEmployeesList.getSelectedValue().replaceAll("[^0-9]", ""));
    }

    public void setEmployeeEmail(String name)
    {
        tfEmployeeEmail.setText(name);
    }


    public String getUsername()
    {
        return tfEmployeeEmail.getText();
    }

    public void setDate(Date date)
    {
        this.tfReportData.setText(date.toString());
    }


}
