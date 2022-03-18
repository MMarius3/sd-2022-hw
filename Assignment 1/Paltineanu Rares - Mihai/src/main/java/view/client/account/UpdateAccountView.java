package view.client.account;

public class UpdateAccountView extends AddAccountView {
    public UpdateAccountView() {
        super();
        initializeFields();
        initializeFrame();
    }

    private void initializeFields() {
        this.getIdField().setEditable(false);
        this.getActionButton().setText("Update");
    }

    private void initializeFrame() {
        setTitle("Update account");
    }
}
