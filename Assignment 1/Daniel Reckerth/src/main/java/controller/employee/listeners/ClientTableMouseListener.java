package controller.employee.listeners;

import service.account.AccountService;
import view.employee.EmployeeView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClientTableMouseListener implements MouseListener {
  private final EmployeeView employeeView;
  private final AccountService accountService;

  public ClientTableMouseListener(EmployeeView employeeView, AccountService accountService) {
    this.employeeView = employeeView;
    this.accountService = accountService;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int row = employeeView.getClientsTable().getSelectedRow();
    if (row != -1) {
      employeeView.setClientInfoOfRow(row);
      employeeView.fillAccountsTable(accountService.findAccountsByClientId(Long.parseLong(employeeView.getClientId())));
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
