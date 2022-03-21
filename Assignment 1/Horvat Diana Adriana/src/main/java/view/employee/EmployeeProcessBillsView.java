package view.employee;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeProcessBillsView extends JFrame {

    private JTextField tfFrom;
    private JTextField tfAmount;
    private JButton btnProcess;

    public EmployeeProcessBillsView(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfFrom);
        add(tfAmount);
        add(btnProcess);
    }

    private void initializeFields() {
        tfFrom = new JTextField("From account id");
        tfAmount = new JTextField("Amount");
        btnProcess = new JButton("Pay");
    }

    public void processBillsBtnListener(ActionListener processBillsBtnListener) {
        btnProcess.addActionListener(processBillsBtnListener);
    }

    public String getTfFrom() {
        return tfFrom.getText();
    }

    public String getTfAmount() {
        return tfAmount.getText();
    }
}
