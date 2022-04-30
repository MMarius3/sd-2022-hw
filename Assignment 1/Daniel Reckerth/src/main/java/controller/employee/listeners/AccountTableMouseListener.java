package controller.employee.listeners;

import view.employee.EmployeeView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AccountTableMouseListener implements MouseListener {
  private final EmployeeView employeeView;

  public AccountTableMouseListener(EmployeeView employeeView) {
    this.employeeView = employeeView;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int row = employeeView.getAccountsTable().getSelectedRow();
    if (row != -1) {
      employeeView.setAccountInfoOfRow(row);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
