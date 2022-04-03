package presentation.ui_action_listeners.menu.general_purpose;

import presentation.controller.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProcessUtilitiesActionListener implements ActionListener {

    private final Menu menu;

    public ProcessUtilitiesActionListener(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(menu.getProcessUtilitiesSum());
    }
}
