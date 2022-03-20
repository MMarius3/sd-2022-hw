package controller;

import view.UtilityView2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ActionsMenuController implements ActionListener {

   private UtilityView2 view2;

    public ActionsMenuController(UtilityView2 v){
        this.view2 = v;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "COMPUTE") {
            String operation = String.valueOf(view2.getActionComboBox().getSelectedItem());
            switch (operation) {
                case "Add client":

                    break;
                case "Subtract":
                    break;
                case "Multiply":
                    break;
                case "Differentiate first":

                    break;
                case "Differentiate second":

                    break;
                case "Integrate first":

                    break;
                case "Integrate second":

                    break;
            }
        }
    }
}
