package model;

import java.time.LocalDate;
import java.util.List;

public class Client {

    private Long id;
    private String name;
    private String id_card_number;
    private String personal_numerical_code;
    private String address;
    private LocalDate created_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getId_card_number() {
        return id_card_number;
    }

    public void setId_card_number(String id_card_number) {
        this.id_card_number = id_card_number;
    }

    public String getPersonal_numerical_code() {
        return personal_numerical_code;
    }

    public void setPersonal_numerical_code(String personal_numerical_code) {
        this.personal_numerical_code = personal_numerical_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at.toString();
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", id_card_number='" + id_card_number + '\'' +
                ", personal_numerical_code='" + personal_numerical_code + '\'' +
                ", address='" + address + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
