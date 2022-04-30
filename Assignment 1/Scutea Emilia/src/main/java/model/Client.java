package model;

public class Client {
    private Long personalNumericalCode;
    private String name;
    private Long cardNumber;
    private String address;
    private Long accountId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getPersonalNumericalCode() {
        return personalNumericalCode;
    }

    public void setPersonalNumericalCode(Long personalNumericalCode) {
        this.personalNumericalCode = personalNumericalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAccount() {
        return accountId;
    }

    public void setAccount(Long accountId) {
        this.accountId = accountId;
    }
}
