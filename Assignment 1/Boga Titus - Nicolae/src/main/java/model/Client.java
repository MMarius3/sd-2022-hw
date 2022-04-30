package model;

import java.util.Calendar;
import java.util.Date;

public class Client {

  private Long id;

  private String name;
  private String cnp;
  private String address;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCnp() {
    return cnp;
  }

  public void setCnp(String cnp) {
    this.cnp = cnp;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
/*
  public Date getAddress() {
    return address;
  }

  public void setAddress(Date address) {
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getAge() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(address);
    int yearOfPublishing = calendar.get(Calendar.YEAR);
    calendar.setTime(new Date());
    int yearToday = calendar.get(Calendar.YEAR);

    return yearToday - yearOfPublishing;
  }
}
*/