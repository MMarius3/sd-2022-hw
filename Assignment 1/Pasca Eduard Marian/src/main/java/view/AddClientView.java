package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClientView extends JFrame{
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
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(addClient);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Add Account window");
        frame.setContentPane(new AddClientView().addClient);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
