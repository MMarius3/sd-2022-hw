package view;

import lombok.Getter;
import view.client.account.AccountView;
import view.client.information.InformationView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class EmployeeView extends JFrame {


    private InformationView informationView;
    private AccountView accountView;

    public EmployeeView() {
        informationView = new InformationView();
        accountView = new AccountView();
    }

}
