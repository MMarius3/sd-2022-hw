package controller;

import dtos.*;
import lombok.Setter;
import lombok.experimental.Accessors;
import mapper.AccountMapper;
import model.Account;
import org.modelmapper.ModelMapper;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.currency.CurrencyRepository;
import repository.user.UserRepositoryMySQL;
import repository.user_action.UserActionRepository;
import service.TransferMoneyBetweenAccounts.TransferMoneyBetweenAccounts;
import service.crud.AccountCRUD;
import service.crud.ClientCRUD;
import service.generate_report.PDFReportGenerator;
import service.process_utilities_bills.ProcessUtilitiesBills;
import validator.AccountDTOValidator;
import validator.ClientDTOValidator;
import view.UserView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Setter
@Accessors(chain = true)
public class UserController extends ControllerActions {

    private final UserView userView;

    private ClientRepository<ClientDTO> clientRepository;
    private CurrencyRepository<CurrencyDTO> currencyRepository;
    private UserActionRepository<UserActionDTO> userActionRepository;
    private AccountRepository<AccountDTO> accountRepository;

    private ModelMapper clientMapper;
    private ModelMapper currencyMapper;
    private ModelMapper userActionMapper;
    private ModelMapper accountMapper;

    public UserController() {
        this.userView = new UserView();
    }

    public void generateReportPDF(String userName, String startDateInput, String endDateInput) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateInput, formatter);
        LocalDate endDate = LocalDate.parse(endDateInput, formatter);

        if (startDate.compareTo(endDate) > 0) {
            this.setFlashError("Start date must be smaller than end date.");
            return;
        }

        new PDFReportGenerator(userActionRepository, userActionMapper)
                .setUserName(userName)
                .setUserRepository(new UserRepositoryMySQL())
                .setUserMapper(new AccountMapper().getMapper())
//                .setPeriod(new Date(String.valueOf(startDate)), new Date(String.valueOf(endDate)))
                .generate("/home/citadin/Desktop");
    }

    public void transferMoney(long senderAccountID, long receiverAccountID, float sum) {

        if (sum == 0) {
            this.setFlashError("Sum must be different from 0.");
            return;
        }

        AccountCRUD accountCRUD = new AccountCRUD();
        accountCRUD
                .setRepository(accountRepository)
                .setMapper(accountMapper);

        Optional<Account> senderAccount = accountCRUD.getByID(senderAccountID);
        Optional<Account> receiverAccount = accountCRUD.getByID(receiverAccountID);

        if (senderAccount.isEmpty() || receiverAccount.isEmpty()) {
            this.setFlashError("Accounts could not have been found. Retry.");
            return;
        }

        try {
            new TransferMoneyBetweenAccounts()
                    .transfer(senderAccount.get(), receiverAccount.get(), sum);
        } catch (Exception e) {
            e.printStackTrace();
            this.setFlashError("Transfer has failed. Please contact the tech group.");
        }
    }

    public void processUtilitiesBills(long accountID, float sum) {

        if (sum == 0) {
            this.setFlashError("Sum must be different from 0.");
            return;
        }

        AccountCRUD accountCRUD = new AccountCRUD();
        accountCRUD
                .setRepository(accountRepository)
                .setMapper(accountMapper);

        Optional<Account> account = accountCRUD.getByID(accountID);

        if (account.isEmpty()) {
            this.setFlashError("No account has been found. Please Retry.");
            return;
        }

        ProcessUtilitiesBills processUtilitiesBills = new ProcessUtilitiesBills();
        processUtilitiesBills
                .setAccountRepository(accountRepository)
                .setAccountMapper(accountMapper)
                .process(account.get(), sum);
    }

    public void createClient(ClientDTO clientDTO) {

        ClientCRUD clientCRUD = (ClientCRUD) new ClientCRUD().
                setRepository(clientRepository).
                setMapper(clientMapper);

        if (!new ClientDTOValidator(clientDTO).validate()) {
            this.setFlashError("Client details are not invalid.");
            return;
        }

        clientCRUD.create(clientDTO);
    }

    public void updateClient(ClientDTO clientDTO) {

        ClientCRUD clientCRUD = (ClientCRUD) new ClientCRUD().
                setRepository(clientRepository).
                setMapper(clientMapper);

        if (!new ClientDTOValidator(clientDTO).validate()) {
            this.setFlashError("Client details are not invalid.");
            return;
        }

        clientCRUD.update(clientDTO);
    }

    public void deleteClient(ClientDTO clientDTO) {

        ClientCRUD clientCRUD = (ClientCRUD) new ClientCRUD().
                setRepository(clientRepository).
                setMapper(clientMapper);

        clientCRUD.deleteByID(clientDTO.getId());
    }

    public void createAccount(AccountDTO accountDTO, long clientID) {

        AccountCRUD accountCRUD = (AccountCRUD) new AccountCRUD().
                setRepository(accountRepository).
                setMapper(accountMapper);

        if (!new AccountDTOValidator(accountDTO).validate()) {
            this.setFlashError("Account details are not invalid.");
            return;
        }

        accountCRUD.create(accountDTO);
    }

    public void updateAccount(AccountDTO accountDTO) {

        AccountCRUD accountCRUD = (AccountCRUD) new AccountCRUD().
                setRepository(accountRepository).
                setMapper(accountMapper);

        if (!new AccountDTOValidator(accountDTO).validate()) {
            this.setFlashError("Account details are not invalid.");
            return;
        }

        accountCRUD.update(accountDTO);
    }

    public void deleteAccount(AccountDTO accountDTO) {

        AccountCRUD accountCRUD = (AccountCRUD) new AccountCRUD().
                setRepository(accountRepository).
                setMapper(accountMapper);

        accountCRUD.deleteByID(accountDTO.getId());
    }

    public UserView getUserView() {
        return this.userView;
    }
}
