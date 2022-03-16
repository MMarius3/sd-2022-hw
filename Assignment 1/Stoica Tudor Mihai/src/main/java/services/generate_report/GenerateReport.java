package services.generate_report;

import repositories.user_action.UserActionMySQL;
import services.generate_report.exceptions.UserActionNotLoadedException;

public class GenerateReport {

    public void generate(AbstractReportGenerator reportGenerator) throws UserActionNotLoadedException {
        reportGenerator.setUserAction(new UserActionMySQL()).
                generate();
    }
}
