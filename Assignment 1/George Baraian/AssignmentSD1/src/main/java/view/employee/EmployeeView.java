package view.employee;

import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JTextField clientId;
    private JTextField clientName;
    private JTextField clientAddress;
    private JTextField clientPersonalNumericalCode;
    private JButton createClientButton;
    private JButton viewClientsButton;
    private JButton updateClientButton;

    public EmployeeView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(clientId);
        add(clientName);
        add(clientAddress);
        add(clientPersonalNumericalCode);
        add(createClientButton);
        add(viewClientsButton);
        add(updateClientButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        clientId = new JTextField();
        clientName = new JTextField();
        clientAddress = new JTextField();
        clientPersonalNumericalCode = new JTextField();
        createClientButton = new JButton("Create a client");
        viewClientsButton = new JButton("View clients");
        updateClientButton = new JButton("Update client");
    }

    public String getId(){
        return clientId.getText();
    }

    public String getName(){
        return clientName.getText();
    }

    public String getAddress(){
        return clientAddress.getText();
    }

    public String getPersonalNumericalCode(){
        return clientPersonalNumericalCode.getText();
    }

    public void setCreateClientButtonListener(ActionListener createClientAccountButtonListener){
        createClientButton.addActionListener(createClientAccountButtonListener);
    }

    public void setViewClientsButtonListener(ActionListener viewClientsButtonListener){
        viewClientsButton.addActionListener(viewClientsButtonListener);
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener){
        updateClientButton.addActionListener(updateClientButtonListener);
    }

    public void createResultTable(List<Client> all) {

        JFrame f = new JFrame();
        String data[][] = new String[all.size()][4];
        for(int i = 0; i < all.size(); i++){
            String id = String.valueOf(all.get(i).getId());
            String name = all.get(i).getName();
            String address = all.get(i).getAddress();
            String personalNumericalCode = String.valueOf(all.get(i).getPersonalNumericalCode());
            //System.out.println("Client " + id + " with name: " + name + " and address: " + address +
                               // " and personal numerical code: " + personalNumericalCode);
            data[i][0] = id;
            data[i][1] = name;
            data[i][2] = address;
            data[i][3] = personalNumericalCode;
        }
        String column[] = {"id", "name", "address", "personalNumericalCode"};

        JTable jT = new JTable(data, column);
        jT.setBounds(30,40,200,300);
        JScrollPane sp = new JScrollPane(jT);
        f.add(sp);
        f.setSize(300,400);
        f.setVisible(true);

    }

    public void setVisible(){
        this.setVisible(true);
    }


}
