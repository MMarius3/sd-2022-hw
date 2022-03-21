package view;

import javax.swing.*;

public class UpdateAccountView extends JFrame{
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel typeLabel;
    private JTextField typeTextField;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton updateAccountButton;
    private JPanel updateAccount;

    public UpdateAccountView() {
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(updateAccount);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Add Account window");
        frame.setContentPane(new UpdateAccountView().updateAccount);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
