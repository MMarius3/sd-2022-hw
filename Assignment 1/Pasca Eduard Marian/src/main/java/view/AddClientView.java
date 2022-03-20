package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClientView {
    private JLabel nameLabel;
    private JTextField NameTextField;
    private JLabel idCardNumberLabel;
    private JTextField icnTextField;
    private JLabel persNumCodeLabel;
    private JTextField pncTextField;
    private JLabel addressLabel;
    private JTextField addressTextField;
    private JButton addClientButton;
    private JPanel addClient;

    public AddClientView() {
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
