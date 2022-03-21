package view;

import database.Constants;
import helpers.Helpers;
import lombok.Getter;
import lombok.Setter;
import model.AccountTypes;
import model.Role;
import model.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class AdminView extends JFrame {

    private JPanel panel;
    private JTable userTable;
    private JTextField email;
    private JButton addRoleButton;
    private JButton deleteRoleButton;
    private JComboBox<String> roleComboBox;
    private JButton generateReportButton;
    private JButton deleteAccountButton;

    private DefaultTableModel userTableModel;
    private List<User> users;

    public AdminView(String title) throws HeadlessException {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        setLocationRelativeTo(null);
        setSize(1000, 700);

        userTableModel = initializeUserTable();


        initializeComboBox();
    }

    private DefaultTableModel initializeUserTable() {
        String[] userTableColumns = {"Nr.", "E-Mail", "Roles"};
        DefaultTableModel userTableModel = new DefaultTableModel(userTableColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.setModel(userTableModel);
        userTable.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
        return userTableModel;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        addUsersToTable(users);
    }

    private void addUsersToTable(List<User> users) {
        userTableModel.setRowCount(0);

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String email = user.getUsername();
            String roles = Helpers.shortRolesString(user.getRoles());

            Object[] data = {i+1, email, roles};
            userTableModel.addRow(data);
        }
    }

    private void initializeComboBox() {
        List<String> values = new ArrayList<>();
        Collections.addAll(values, Constants.Roles.ROLES);
        for (String value : values) {
            roleComboBox.addItem(value);
        }
        roleComboBox.setSelectedItem(null);
    }
}
