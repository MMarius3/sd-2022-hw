package controller.admin;

import model.validator.UserValidator;
import service.user.UserService;
import view.admin.ActionEmployeeView;
import view.admin.ActionEmployeeView;
import view.admin.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployeeController {
    private final AdminView adminView;
    private final ActionEmployeeView addEmployeeView;
    private final UserService userService;
    private final UserValidator userValidator;
    public AddEmployeeController(AdminView adminView,
                                 ActionEmployeeView addEmployeeView,
                                 UserService userService,
                                 UserValidator userValidator) {
        this.adminView = adminView;
        this.addEmployeeView = addEmployeeView;
        this.userService = userService;
        this.userValidator = userValidator;
        addEmployeeView.setCancelButtonListener(new CancelButtonListener());
        addEmployeeView.setAddEmployeeButtonListener(new AddButtonListener());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addEmployeeView.setVisible(false);
        }
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
