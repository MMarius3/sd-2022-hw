package view;

import javax.swing.*;

public class TransferMoneyView extends JFrame {
    private JLabel senderLabel;
    private JTextField senderTextField;
    private JTextField receiverTextField;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton performTransferButton;
    private JPanel transferMoney;

    public TransferMoneyView() {
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(transferMoney);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Add Account window");
        frame.setContentPane(new TransferMoneyView().transferMoney);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
