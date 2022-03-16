package services.generate_report;

import services.generate_report.exceptions.UserActionNotLoadedException;

public class GenerateReportPDF extends AbstractReportGenerator {

    @Override
    public void generate() throws UserActionNotLoadedException {
        if (userActionDTO == null) {
            throw new UserActionNotLoadedException();
        }
    }
}
