package controller;

import view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {
    private final AdminView adminView;

    public AdminController(AdminView adminView){
        this.adminView = adminView;
        adminView.setBtnCreateListener(new AdminController.BtnCreateListener());
        adminView.setBtnUpdateListener(new AdminController.BtnUpdateListener());
        adminView.setBtnReadListener(new AdminController.BtnReadListener());
        adminView.setBtnDeleteListener(new AdminController.BtnDeleteListener());

    }

    public class BtnCreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class BtnUpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class BtnReadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class BtnDeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
