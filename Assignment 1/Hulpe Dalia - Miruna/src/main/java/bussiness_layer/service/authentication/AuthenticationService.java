package bussiness_layer.service.authentication;

import bussiness_layer.models.User;

public interface AuthenticationService {

  User login(String username, String password);

}
