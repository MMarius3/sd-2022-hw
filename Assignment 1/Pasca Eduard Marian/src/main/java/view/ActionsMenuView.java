package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionsMenuView extends JFrame {
    private final JComboBox actionComboBox;
    private JLabel actionLabel;
    private JButton performSelectedButton;

    public ActionsMenuView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        String[] operations = {"Add client", "Add account", "Update client", "Update account", "Delete account", "View clients", "View accounts", "Transfer money"};
        this.actionComboBox = new JComboBox(operations);
        add(actionLabel);
        add(actionComboBox);
        add(performSelectedButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        actionLabel = new JLabel("Action: ");
        performSelectedButton = new JButton("Perform action");
    }


    public String getActionComboBox() {
        return String.valueOf(actionComboBox.getSelectedItem());
    }

    public void addPerformSelectedButtonListener(ActionListener performSelectedButtonListener){
        performSelectedButton.addActionListener(performSelectedButtonListener);
    }
}
