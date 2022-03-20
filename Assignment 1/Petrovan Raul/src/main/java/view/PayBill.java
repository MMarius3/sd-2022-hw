package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Getter
@Setter
public class PayBill extends JFrame{
    private JTextField billName;
    private JTextField billValue;
    private JButton payBillButton;
    private JPanel panel;

    public PayBill(String title) {
        super(title);


        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel);
        setLocationRelativeTo(null);
        setSize(500, 300);
//        this.pack();
    }

}
