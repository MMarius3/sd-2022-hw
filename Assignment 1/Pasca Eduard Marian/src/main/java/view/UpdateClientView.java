package view;

import javax.swing.*;

public class UpdateClientView extends JFrame{
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel icnLabel;
    private JTextField icnTextField;
    private JTextField pncTextField;
    private JTextField addressTextField;
    private JLabel addressLabel;
    private JButton updateClientButton;
    private JPanel updateClient;

    public UpdateClientView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(updateClient);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Add Account window");
        frame.setContentPane(new UpdateClientView().updateClient);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
