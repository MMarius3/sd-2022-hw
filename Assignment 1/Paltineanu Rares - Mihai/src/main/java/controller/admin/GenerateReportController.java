package controller.admin;

import model.Action;
import model.validator.DateValidator;
import service.action.ActionService;
import view.admin.AdminView;
import view.admin.ReportEmployeeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class GenerateReportController {
    private final AdminView adminView;
    private final ActionService actionService;

    public GenerateReportController(AdminView adminView, ActionService actionService) {
        this.adminView = adminView;
        this.actionService = actionService;
        setButtonListener();
    }

    private void setButtonListener() {
        this.adminView.setGenerateReportButtonListener(new GenerateReportButtonListener());
    }

    private class GenerateReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fromYear = adminView.getFromYearField().getText();
            String fromMonth = adminView.getFromMonthField().getText();
            String fromDay = adminView.getFromDayField().getText();

            String toYear = adminView.getToYearField().getText();
            String toMonth = adminView.getToMonthField().getText();
            String toDay = adminView.getToDayField().getText();

            DateValidator dateValidator = new DateValidator();
            dateValidator.validate(fromYear, fromMonth, fromDay, toYear, toMonth, toDay);

            List<String> errors = dateValidator.getErrors();

            if(errors.isEmpty()) {
                int fromYearInt = Integer.parseInt(fromYear);
                int fromMonthInt = Integer.parseInt(fromMonth);
                int fromDayInt = Integer.parseInt(fromDay);

                int toYearInt = Integer.parseInt(toYear);
                int toMonthInt = Integer.parseInt(toMonth);
                int toDayInt = Integer.parseInt(toDay);

                Date fromDate = new Date(fromYearInt - 1900, fromMonthInt - 1, fromDayInt);
                Date toDate = new Date(toYearInt-  1900, toMonthInt - 1, toDayInt);
                String id = adminView.getUserIdField().getText();

                if(!isIdLong(id)) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Id must be long");
                    return;
                }

                Long userId = Long.parseLong(id);

                List<model.Action> actions = actionService.filterByDateAndId(userId, fromDate, toDate);

                ReportEmployeeView reportEmployeeView = new ReportEmployeeView();

                DefaultTableModel defaultTableModel = (DefaultTableModel) reportEmployeeView.getReportsTable().getModel();
                defaultTableModel.setRowCount(0);
                for(Action action: actions) {
                    final String[] row = new String[]{String.valueOf(action.getId()),
                            String.valueOf(action.getUser_id()),
                            action.getAction(),
                            String.valueOf(action.getDate())
                    };
                    defaultTableModel.addRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), dateValidator.getFormattedErrors());
            }
        }

        private boolean isIdLong(String id) {
            try {
                Long.parseLong(id);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
