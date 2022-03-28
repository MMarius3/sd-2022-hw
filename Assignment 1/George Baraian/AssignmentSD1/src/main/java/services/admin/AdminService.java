package services.admin;

import model.validation.Notification;

public interface AdminService {

    Notification<Boolean> createEmployee(String username, String password);



}
