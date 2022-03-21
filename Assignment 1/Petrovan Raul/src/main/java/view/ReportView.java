package view;

import lombok.Getter;
import lombok.Setter;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.HeadlessException;
import java.util.Properties;

@Getter
@Setter
public class ReportView extends JFrame {
    private JButton generateReportButton;
    private JTable table1;
    private JPanel fromPanel;
    private JPanel toPanel;
    private JPanel panel;

    public ReportView(String title) throws HeadlessException {
        super(title);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel);
        setLocationRelativeTo(null);
        setSize(800, 600);

        UtilDateModel model = new UtilDateModel();
            //model.setDate(20,04,2014);
            // Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        fromPanel.add(datePicker);

        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateComponentFormatter());
        toPanel.add(datePicker2);
    }
}
