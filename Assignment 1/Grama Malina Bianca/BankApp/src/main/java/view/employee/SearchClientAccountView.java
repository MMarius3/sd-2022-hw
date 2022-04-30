package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class SearchClientAccountView extends JFrame {
    private JTextField clientId;
    private JButton search;

    public SearchClientAccountView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(clientId);
        add(search);
    }

    private void initializeFields() {
        clientId = new JTextField("Type the id for the client you wish to display the accounts");
        search = new JButton("Search");
    }

    public String getClientId() {
        return clientId.getText();
    }

    public void setSearchClientButtonListener(ActionListener searchClientButtonListener) {
        search.addActionListener(searchClientButtonListener);
    }

}
