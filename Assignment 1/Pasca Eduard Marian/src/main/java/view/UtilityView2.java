package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UtilityView2 extends JFrame{
    private JPanel UtilityPanel;
    private JComboBox ActionComboBox;
    private JLabel ActionLabel;
    private JButton performSelectedButton;

    public UtilityView2() throws HeadlessException {
        frameInit();
        setSize(300, 300);
        setLocationRelativeTo(null);
        UtilityPanel.setLayout(new GridLayout(2,2));
        String[] operations = {"Add client", "Add account", "Update client", "Update account", "Delete account", "View clients", "View accounts", "Transfer money"};
        this.ActionComboBox = new JComboBox(operations);
        UtilityPanel.add(ActionComboBox);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(UtilityPanel);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Actions window");
        frame.setContentPane(new UtilityView2().UtilityPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getUtilityPanel() {
        return UtilityPanel;
    }

    public void setUtilityPanel(JPanel utilityPanel) {
        UtilityPanel = utilityPanel;
    }

    public JComboBox getActionComboBox() {
        return ActionComboBox;
    }

    public void setActionComboBox(JComboBox actionComboBox) {
        ActionComboBox = actionComboBox;
    }

    public JLabel getActionLabel() {
        return ActionLabel;
    }

    public void setActionLabel(JLabel actionLabel) {
        ActionLabel = actionLabel;
    }

    public JButton getPerformSelectedButton() {
        return performSelectedButton;
    }

    public void setPerformSelectedButton(JButton performSelectedButton) {
        this.performSelectedButton = performSelectedButton;
    }

    public void addPerformSelectedButtonListener(ActionListener performSelectedButtonListener){
        performSelectedButton.addActionListener(performSelectedButtonListener);
    }
}
