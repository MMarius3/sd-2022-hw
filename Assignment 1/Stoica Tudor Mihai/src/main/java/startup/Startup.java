package startup;

import controller.UserController;
import mapper.AccountMapper;
import mapper.ClientMapper;
import mapper.CurrencyMapper;
import mapper.UserActionMapper;
import model.Client;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.currency.CurrencyRepositoryMySQL;
import repository.user_action.UserActionRepositoryMySQL;
import service.crud.ClientCRUD;
import view.component.ClientsDetailsBuilder;
import view.component.ClientsListBuilder;
import view.component.ReportGeneratorFormBuilder;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Startup {

    public Startup() {


        ClientCRUD clientCRUD = (ClientCRUD) new ClientCRUD()
                .setMapper(new ClientMapper().getMapper())
                .setRepository(new ClientRepositoryMySQL());

        List<Client> clients = clientCRUD.getAllClients();

        UserController userController = new UserController();

        userController
                .setAccountRepository(new AccountRepositoryMySQL())
                .setClientRepository(new ClientRepositoryMySQL())
                .setUserActionRepository(new UserActionRepositoryMySQL())
                .setCurrencyRepository(new CurrencyRepositoryMySQL())

                .setAccountMapper(new AccountMapper().getMapper())
                .setClientMapper(new ClientMapper().getMapper())
                .setUserActionMapper(new UserActionMapper().getMapper())
                .setCurrencyMapper(new CurrencyMapper().getMapper())

                .getUserView()
                .setComponents(new ReportGeneratorFormBuilder().setUserController(userController).buildDefaultForm())
                .setComponents(new ClientsListBuilder().setClientsList(clients))
                .setComponents(new ClientsDetailsBuilder(clients))
                .setVisible(true);
    }

    public static void main(String[] args) {
        new Startup();
    }
}
