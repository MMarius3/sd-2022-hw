package model;

import database.enums.RoleType;

import java.util.List;

public class Role {
  private Long id;
  private RoleType role;
  private List<Right> rights;

  public Role(Long id, RoleType role, List<Right> rights) {
    this.id = id;
    this.role = role;
    this.rights = rights;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleType getRole() {
    return role;
  }

  public void setRole(RoleType role) {
    this.role = role;
  }

  public List<Right> getRights() {
    return rights;
  }

  public void setRights(List<Right> rights) {
    this.rights = rights;
  }
}
