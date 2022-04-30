package view;

public class ClientDTO {
    private String name;
    private String address;
    private Integer identificationNr;
    private String personalNumericalCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIdentificationNr() {
        return identificationNr;
    }

    public void setIdentificationNr(Integer identificationNr) {
        this.identificationNr = identificationNr;
    }

    public String getPersonalNumericalCode() {
        return personalNumericalCode;
    }

    public void setPersonalNumericalCode(String personalNumericalCode) {
        this.personalNumericalCode = personalNumericalCode;
    }
}
