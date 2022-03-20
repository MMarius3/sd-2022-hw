package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AddClientView extends JFrame {

    private JTextField name;
    private JTextField card_no;
    private JTextField cnp;
    private JTextField address;
    private JButton addClient;

    public AddClientView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(name);
        add(card_no);
        add(cnp);
        add(address);
        add(addClient);
    }

    private void initializeFields() {
        name = new JTextField("name");
        card_no = new JTextField("identity card number");
        cnp = new JTextField("cnp");
        address = new JTextField("address");
        addClient = new JButton("Add Client");
    }

    public String getName() {
        return name.getText();
    }

    public String getICNo() {
        return card_no.getText();
    }

    public String getCnp() {
        return cnp.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public void setAddClientButtonListener(ActionListener addClientButtonListener) {
        addClient.addActionListener(addClientButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

}
