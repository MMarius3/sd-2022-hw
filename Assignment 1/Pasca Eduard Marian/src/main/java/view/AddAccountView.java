package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountView extends JFrame{
    private JTextField typeTextField;
    private JTextField amountTextField;
    private JTextField dateTextField;
    private JButton addAccountButton;
    private JPanel addAccount;

    public AddAccountView() {
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(addAccount);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Add Account window");
        frame.setContentPane(new AddAccountView().addAccount);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
