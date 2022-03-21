package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class ProcessUtilitiesBillsView extends JFrame{
    private JTextField id;
    private JTextField amount;
    private JButton process;

    public ProcessUtilitiesBillsView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(id);
        add(amount);
        add(process);
    }

    private void initializeFields() {
        id = new JTextField("id of account");
        amount = new JTextField("amount of bill");
        process = new JButton("Process Bill");
    }

    public String getId() {
        return id.getText();
    }

    public String getAmount() {
        return amount.getText();
    }

    public void setProcessBillsBtnListener(ActionListener processBillsBtnListener){
        process.addActionListener(processBillsBtnListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

}
