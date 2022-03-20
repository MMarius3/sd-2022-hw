package view;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {
    private JButton adminBtn;

    public AdminView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(adminBtn);
    }

    private void initializeFields() {
        adminBtn = new JButton("Administrator");
    }

    public void setVisible() {
        this.setVisible(true);
    }
}
