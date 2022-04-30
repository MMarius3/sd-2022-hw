package view;

import lombok.Getter;
import view.client.account.AccountView;
import view.client.information.InformationView;

import javax.swing.*;

@Getter
public class EmployeeView extends JFrame {

    private final InformationView informationView;
    private final AccountView accountView;

    public EmployeeView() {
        informationView = new InformationView();
        accountView = new AccountView();
        accountView.setClientsTableReordering(false);
        informationView.setClientsTableReordering(false);
    }

}
