package view.client.information;

import lombok.Getter;

@Getter
public class UpdateInformationView extends AddInformationView{


    public UpdateInformationView() {
        super();
        initializeFields();
        initializeFrame();
    }

    private void initializeFields() {
        this.addInformationButton.setText("Update information");
    }

    private void initializeFrame() {
        setTitle("Update information");
    }


}
