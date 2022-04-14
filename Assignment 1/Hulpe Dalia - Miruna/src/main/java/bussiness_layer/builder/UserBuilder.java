package bussiness_layer.builder;

import bussiness_layer.models.Role;
import bussiness_layer.models.User;

import java.util.List;

public class UserBuilder {

  private User user;

  public UserBuilder() {
    user = new User();
  }

  public UserBuilder setRoles(List<Role> roles) {
    user.setRoles(roles);
    return this;
  }

  public UserBuilder setUsername(String username) {
    user.setUsername(username);
    return this;
  }

  public UserBuilder setPassword(String password) {
    user.setPassword(password);
    return this;
  }

  public UserBuilder setId_series(String id_series) {
    user.setId_series(id_series);
    return this;
  }

  public UserBuilder setId_number(Long id_number) {
    user.setId_number(id_number);
    return this;
  }

  public UserBuilder setPnc(String pnc) {
    user.setPnc(pnc);
    return this;
  }

  public UserBuilder setAddress(String address) {
    user.setAddress(address);
    return this;
  }

  public UserBuilder setId(Long id) {
    user.setId(id);
    return this;
  }

  public User build() {
    return user;
  }


}
