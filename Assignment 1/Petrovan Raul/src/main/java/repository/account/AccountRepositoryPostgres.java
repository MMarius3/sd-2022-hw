package repository.account;

import lombok.RequiredArgsConstructor;
import model.Account;
import model.Bill;
import helpers.validation.Notification;
import repository.user.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

@RequiredArgsConstructor
public class AccountRepositoryPostgres implements AccountRepository{

    private final Connection connection;
    private final UserRepository userRepository;


    @Override
    public Notification<Account> addAccountToClient(Account a, Long clientId) {
        Notification<Account> newAccountNotification = new Notification<>();
        try {
            PreparedStatement insertAccount = connection
                    .prepareStatement("insert into account (user_id, balance, type, " +
                                    "creation_date, account_number) VALUES ((select \"user\".id " +
                                    "from \"user\" join client c on \"user\".id = c.user_id where" +
                                    " c.id = ?), ?, ?, date(now()), ?);",
                            Statement.RETURN_GENERATED_KEYS);
            insertAccount.setLong(1, clientId);
            insertAccount.setFloat(2, a.getBalance());
            insertAccount.setString(3, a.getType().toString());
            insertAccount.setString(4, a.getAccountNumber());

            insertAccount.executeUpdate();

            ResultSet rs = insertAccount.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong("id");
            Date creationDate = rs.getDate("creation_date");
            a.setId(accountId);
            a.setCreationDate(creationDate);
            newAccountNotification.setResult(a);
            return newAccountNotification;
        } catch (SQLException e) {
            newAccountNotification.addError("Error with database");
            return newAccountNotification;
        }
    }

    @Override
    public Notification<Boolean> updateAccount(Account a) {
        Notification<Boolean> updateAccountNotification = new Notification<>();
        try {
            PreparedStatement updateAccount = connection
                    .prepareStatement("update account set balance = ?, type = ?, account_number " +
                            "= ? where id = ?;");
            updateAccount.setFloat(1, a.getBalance());
            updateAccount.setString(2, a.getType().toString());
            updateAccount.setString(3, a.getAccountNumber());
            updateAccount.setLong(4, a.getId());

            updateAccount.executeUpdate();

            updateAccountNotification.setResult(true);
            return updateAccountNotification;
        } catch (SQLException e) {
            updateAccountNotification.addError("Error with database");
            return updateAccountNotification;
        }
    }

    @Override
    public Notification<Boolean> deleteAccount(Long accountId) {
        Notification<Boolean> deleteAccountNotification = new Notification<>();
        try {

            PreparedStatement deleteAccount = connection
                    .prepareStatement(
                            "delete from account where id = " + accountId + ";");
            deleteAccount.executeUpdate();

            deleteAccountNotification.setResult(true);
            return deleteAccountNotification;
        } catch (SQLException e) {
            deleteAccountNotification.addError("Error with database");
            return deleteAccountNotification;
        }
    }

    @Override
    public Notification<Boolean> transferMoney(String from_account, String to_account, float value) {
        Notification<Boolean> transferMoneyNotification = new Notification<>();
        try {
            System.out.println(from_account + to_account + value);
            connection.setAutoCommit(false);
            PreparedStatement takeFrom = connection
                    .prepareStatement(
                            "update account set balance = (balance -" + value + ") where " +
                                    "account_number " + "like '" + from_account + "';");
            takeFrom.executeUpdate();
            PreparedStatement addTo = connection
                    .prepareStatement(
                            "update account set balance = (balance +" + value + ") where " +
                                    "account_number " + "like '" + to_account + "';");
            addTo.executeUpdate();
            connection.commit();

            transferMoneyNotification.setResult(true);
            return transferMoneyNotification;
        } catch (SQLException e) {
            transferMoneyNotification.addError("Error with database");
            return transferMoneyNotification;
        }
    }

    @Override
    public Notification<Boolean> payBill(Long from_account, Bill bill) {
        Notification<Boolean> transferMoneyNotification = new Notification<>();
        try {
            PreparedStatement takeFrom = connection
                    .prepareStatement(
                            "update account set balance = (balance -" + bill.getValue() + ") where " +
                                    "account_number " + "like '" + from_account + "';");
            takeFrom.executeUpdate();

            transferMoneyNotification.setResult(true);
            return transferMoneyNotification;
        } catch (SQLException e) {
            transferMoneyNotification.addError("Error with database");
            return transferMoneyNotification;
        }
    }
}
