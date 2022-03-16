package services;

import models.Client;

public interface AdminUserClientInformationActions extends RegularUserClientInformationActions {
    void delete(Client client);
}
