package view;

import javax.swing.*;

public class DeleteAccountView extends JFrame{
    private JTextField idTextField;
    private JLabel idLabel;
    private JButton deleteAccountButton;
    private JPanel deleteAccount;

    public DeleteAccountView() {
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(deleteAccount);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Add Account window");
        frame.setContentPane(new DeleteAccountView().deleteAccount);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
