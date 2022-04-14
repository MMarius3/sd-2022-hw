package service;

import model.Client;

public interface AdminUserClientInformationActions extends RegularUserClientInformationActions {
    void delete(Client client);
}
