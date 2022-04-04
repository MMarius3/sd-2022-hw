package controller.employee;

import model.Account;
import model.builder.AccountBuilder;
import services.account.AccountService;
import view.employee.AccountView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AccountController {

    private final AccountView accountView;
    private final AccountService accountService;

    public AccountController(AccountView accountView, AccountService accountService) {
        this.accountView = accountView;
        this.accountService = accountService;
        accountView.setCreateAccountButton(new CreateAccountButtonListener());
        accountView.setDeleteAccountsButton(new DeleteAccountsButtonListener());
        accountView.setViewClientAccountsButton(new ViewClientAccountsButtonListener());
    }


    private class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long accountId = Long.valueOf(accountView.getId());
            String accountType = accountView.getAccountType();
            Long accountAmount = Long.valueOf(accountView.getAmount());
            Long clientID = Long.valueOf(accountView.getClientID());
            Account account1 = new AccountBuilder().setId(accountId).setType(accountType).setAmount(accountAmount)
                                                    .setClientID(clientID).build();
            accountService.save(account1);
        }
    }

    private class DeleteAccountsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long clientID = Long.valueOf(accountView.getClientID());
            accountService.deleteByClientID(clientID);
        }
    }

    private class ViewClientAccountsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long clientId = Long.valueOf(accountView.getClientID());
            List<Account> all = accountService.findByClientId(clientId);
            accountView.createResultTable(all);
        }
    }
}
