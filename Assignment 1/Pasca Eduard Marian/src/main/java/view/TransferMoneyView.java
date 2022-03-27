package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TransferMoneyView extends JFrame {
    private JLabel senderLabel;
    private JLabel receiverLabel;
    private JTextField senderTextField;
    private JTextField receiverTextField;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton performTransferButton;

    public TransferMoneyView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(senderLabel);
        add(senderTextField);
        add(receiverLabel);
        add(receiverTextField);
        add(amountLabel);
        add(amountTextField);
        add(performTransferButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        senderLabel = new JLabel("Sender ID:");
        senderTextField = new JTextField();
        receiverLabel = new JLabel("Receiver ID:");
        receiverTextField = new JTextField();
        amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField();
        performTransferButton = new JButton("Perform transfer");
    }

    public Long getSenderId(){ return Long.valueOf(senderTextField.getText()); }

    public Long getReceiverId(){ return Long.valueOf(receiverTextField.getText()); }

    public void performTransferButtonListener(ActionListener performTransferBtnListener){
        performTransferButton.addActionListener(performTransferBtnListener);
    }
}
