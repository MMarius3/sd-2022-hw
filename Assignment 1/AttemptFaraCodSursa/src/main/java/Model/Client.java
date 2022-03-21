package Model;

public class Client {
    private int id;
    private String name;
    private Long idcardnumber;
    private Long cnp;
    private String address;
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Long getCnp() {
        return cnp;
    }

    public Long getIdcardnumber() {
        return idcardnumber;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public void setIdcardnumber(Long idcardnumber) {
        this.idcardnumber = idcardnumber;
    }

}
