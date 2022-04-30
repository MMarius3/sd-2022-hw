package model;

import database.enums.RightType;

public class Right {

  private Long id;
  private RightType right;

  public Right(Long id, RightType right) {
    this.id = id;
    this.right = right;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RightType getRight() {
    return right;
  }

  public void setRight(RightType right) {
    this.right = right;
  }
}
