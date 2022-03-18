package view.client.account;

import javax.swing.*;
import java.awt.*;

public class TransferMoneyView extends JFrame {
    private Container container;

    private JLabel fromAccountLabel;
    private JLabel toAccountLabel;
    private JLabel moneyAmountLabel;

    private JTextField fromAccountField;
    private JTextField toAccountField;
    private JTextField moneyAmountField;
    public TransferMoneyView() {

    }

    private void initializeFrame(){
        setSize(660, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(false);
        setTitle("Transfer money");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        fromAccountLabel = new JLabel();
        toAccountLabel = new JLabel();
        moneyAmountLabel = new JLabel();
    }
}
