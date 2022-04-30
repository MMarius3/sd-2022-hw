package view.employee;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeTransferMoneyView extends JFrame {

    private JTextField tfFrom;
    private JTextField tfTo;
    private JTextField tfAmount;
    private JButton btnTransfer;

    public EmployeeTransferMoneyView(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfFrom);
        add(tfTo);
        add(tfAmount);
        add(btnTransfer);
    }

    private void initializeFields() {
        tfFrom = new JTextField("From account id");
        tfTo = new JTextField("To account id");
        tfAmount = new JTextField("Amount");
        btnTransfer = new JButton("Transfer money");
    }

    public void transferMoneyBtnListener(ActionListener transferMoneyBtnListener) {
        btnTransfer.addActionListener(transferMoneyBtnListener);
    }

    public String getTfFrom() {
        return tfFrom.getText();
    }

    public String getTfTo() {
        return tfTo.getText();
    }

    public String getTfAmount() {
        return tfAmount.getText();
    }
}
