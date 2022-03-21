package bussiness_layer.models;

import java.util.List;

public class User {

  private Long id;
  private String username;
  private String password;
  private String id_series;
  private Long id_number;
  private String pnc;
  private String address;
  private List<Account> accounts;
  private List<Role> roles;

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public User ()
  {
  }

  public User (String username, String password, String id_series, Long id_number, String pnc, String address)
  {
    this.username = username;
    this.password = password;
    this.id_series = id_series;
    this.id_number = id_number;
    this.pnc = pnc;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getId_series() {
    return id_series;
  }

  public void setId_series(String id_series) {
    this.id_series = id_series;
  }

  public Long getId_number() {
    return id_number;
  }

  public void setId_number(Long id_number) {
    this.id_number = id_number;
  }

  public String getPnc() {
    return pnc;
  }

  public void setPnc(String pnc) {
    this.pnc = pnc;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
