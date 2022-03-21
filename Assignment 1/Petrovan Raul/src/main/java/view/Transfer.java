package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Getter
@Setter
public class Transfer extends JFrame {
    private JTextField from;
    private JTextField to;
    private JPanel panel;
    private JButton makeTransferButton;
    private JTextField valueField;


    public Transfer(String title) {
        super(title);


        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel);
        setLocationRelativeTo(null);
        setSize(500, 300);
//        this.pack();
    }
}
