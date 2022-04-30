package view.admin;

import model.EmployeeActivity;
import model.User;
import view.employee.EmployeeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class AdminView extends JFrame{
  private JPanel panel1;
  private JTextField emailTxtFld;
  private JTextField passwordTxtFld;
  private JTable employeesTable;
  private JTable activitiesTable;
  private JButton updateButton;
  private JButton deleteButton;
  private JTextField employeeId;

  public AdminView(){
    setContentPane(panel1);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    pack();
    init();
  }

  private void init() {
    employeeId.setEditable(false);

    employeesTable.setModel(new DefaultTableModel(null,
            new String[]{"ID", "Email"}));

    activitiesTable.setModel(new DefaultTableModel(null,
            new String[]{"ID", "Performed At", "Employee", "Performed Activity"}));
  }

  public String getEmail() {
    return emailTxtFld.getText();
  }

  public String getPassword() {
    return passwordTxtFld.getText();
  }

  public String getEmployeeId() {
    return employeeId.getText();
  }

  public void setUpdateButtonActionListener(ActionListener listener) {
    updateButton.addActionListener(listener);
  }

  public void setDeleteButtonActionListener(ActionListener listener) {
    deleteButton.addActionListener(listener);
  }

  public void setEmployeeTableMouseListener(MouseListener listener) {
    employeesTable.addMouseListener(listener);
  }

  public void showMessage(String message, int type) {
    JOptionPane.showMessageDialog(this, message, "Message", type);
  }

  public void fillEmployeeTable(List<User> users){
    DefaultTableModel model = (DefaultTableModel) employeesTable.getModel();
    model.setRowCount(0);
    for (User user : users) {
      model.addRow(new Object[]{user.getId(), user.getUsername(), user.getPassword()});
    }
  }

  public void fillActivitiesTable(List<EmployeeActivity> activities){
    DefaultTableModel model = (DefaultTableModel) activitiesTable.getModel();
    model.setRowCount(0);
    for (EmployeeActivity activity : activities) {
      model.addRow(new Object[]{activity.getId(), activity.getPerformedAt(), activity.getEmployee().getUsername(), activity.getPerformedActivity().getRight().getLabel()});
    }
  }

  public void setEmployeeInfoOfRow(int row) {
    String id = employeesTable.getValueAt(row, 0).toString();
    String email = employeesTable.getValueAt(row, 1).toString();
    employeeId.setText(id);
    emailTxtFld.setText(email);
  }

  public JTable getEmployeesTable() {
    return employeesTable;
  }

  public JTable getActivitiesTable() {
    return activitiesTable;
  }

}
