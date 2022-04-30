package model;

public class Client {
    private Long id;

    private String name;
    private Integer idCardNumber;
    private Long idCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(Integer idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Long getIdCode() {
        return idCode;
    }

    public void setIdCode(Long idCode) {
        this.idCode = idCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
