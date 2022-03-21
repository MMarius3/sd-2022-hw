package view;

import javax.swing.*;
import java.awt.*;

public class ReportView extends JFrame {
    private JTextArea reportArea;

    public ReportView(){
        setSize(200,400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new GridLayout(0,1));

        add(reportArea);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields(){
        reportArea = new JTextArea();
        reportArea.setSize(300,200);
        reportArea.setEditable(false);
    }

    public void setReport(String s) {reportArea.setText(s);}
}
