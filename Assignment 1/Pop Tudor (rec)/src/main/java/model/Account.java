package model;

import java.time.LocalDate;

public class Account {
    private Long id;
    private Integer identificationNr;
    private String type;
    private Integer moneyAmount;
    private LocalDate creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdentificationNr() {
        return identificationNr;
    }

    public void setIdentificationNr(Integer identificationNr) {
        this.identificationNr = identificationNr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
