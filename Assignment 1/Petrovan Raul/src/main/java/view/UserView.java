package view;

import lombok.Getter;
import lombok.Setter;
import model.Account;
import model.AccountTypes;
import model.Client;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class UserView extends JFrame{
    private JTable usersTable;
    private JPanel panel1;
    private JTable accountsTable;
    private JButton transferMoneyButton;
    private JTextField accountNumber;
    private JTextField nameField;
    private JTextField idNumber;
    private JTextField CNP;
    private JTextField address;
    private JTextField balance;
    private JTextField email;
    private JButton createAccount;
    private JButton deleteAccount;
    private JButton updateAccount;
    private JButton createClient;
    private JButton deleteClient;
    private JButton updateClient;
    private JComboBox<String> typeComboBox;
    private JButton payBillButton;

    private List<Client> clients;
    private DefaultTableModel clientTableModel, accountTableModel;

    public UserView(String title) {
        super(title);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        setLocationRelativeTo(null);
        setSize(1000, 700);
//        this.pack();
        clientTableModel = initializeClientTable();
        accountTableModel = initializeAccountsTable();
        initializeComboBox();
    }

    private void initializeComboBox() {
        List<String> values = new ArrayList<>();
        for(AccountTypes accountType : AccountTypes.values()) {
            values.add(accountType.toString());
        }
        for (String value : values) {
            typeComboBox.addItem(value);
            System.out.println("da");
        }
        typeComboBox.setSelectedIndex(0);
    }


    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        addClientsToTable(clients);
    }

    private void addClientsToTable(List<Client> clients) {
        clientTableModel.setRowCount(0);

        for(int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            String name = client.getName();
            String id = client.getIdNumber();
            String cnp = client.getCNP();
            String address = client.getAddress();

            Object[] data = {i+1, name, id, cnp, address};
            clientTableModel.addRow(data);
        }
    }

    public void addAccountsToTable(List<Account> accounts) {
        accountTableModel.setRowCount(0);

        for(int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            String number = account.getAccountNumber();
            String balance = Float.toString(account.getBalance());
            String date = account.getCreationDate().toString();
            String type = Objects.toString(account.getType(), null);

            Object[] data = {i+1, number, balance, date, type};
            accountTableModel.addRow(data);
        }
    }

    private DefaultTableModel initializeClientTable() {
        String[] clientTableColumns = {"Nr.", "Name", "idNumber", "CNP", "Address"};
        DefaultTableModel clientTableModel = new DefaultTableModel(clientTableColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable.setModel(clientTableModel);
        usersTable.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
        return clientTableModel;
    }

    private DefaultTableModel initializeAccountsTable() {
        String[] accountTableColumns = {"Nr.", "Account Number", "Balance", "Creation Date",
                "Account Type"};
        DefaultTableModel accountTableModel = new DefaultTableModel(accountTableColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        accountsTable.setModel(accountTableModel);
        accountsTable.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
        return accountTableModel;
    }

}
