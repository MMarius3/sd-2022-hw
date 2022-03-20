package view;

import controller.ActionsMenuController;

import javax.swing.*;
import java.awt.*;

public class UtilityView2 extends JFrame{
    private JPanel UtilityPanel;
    private JComboBox ActionComboBox;
    private JLabel ActionLabel;
    private JTable UtilityTable;
    private JButton performSelectedButton;

    ActionsMenuController controller = new ActionsMenuController(this);

    public UtilityView2() throws HeadlessException {

        setSize(300, 300);
        setLocationRelativeTo(null);

        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new UtilityView2().UtilityPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.performSelectedButton = new JButton();
        this.performSelectedButton.setActionCommand("CHOOSE");
        this.performSelectedButton.addActionListener(this.controller);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(UtilityPanel);
        this.pack();
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

    public JTable getUtilityTable() {
        return UtilityTable;
    }

    public void setUtilityTable(JTable utilityTable) {
        UtilityTable = utilityTable;
    }

    public JButton getPerformSelectedButton() {
        return performSelectedButton;
    }

    public void setPerformSelectedButton(JButton performSelectedButton) {
        this.performSelectedButton = performSelectedButton;
    }

    public ActionsMenuController getController() {
        return controller;
    }

    public void setController(ActionsMenuController controller) {
        this.controller = controller;
    }
}
