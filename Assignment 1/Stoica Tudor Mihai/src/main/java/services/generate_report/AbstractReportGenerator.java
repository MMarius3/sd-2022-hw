package services.generate_report;

import repositories.dtos.UserActionDTO;
import repositories.user_action.UserAction;
import services.generate_report.exceptions.UserActionNotLoadedException;

public abstract class AbstractReportGenerator {

    private UserAction<UserActionDTO> userAction;
    protected services.generate_report.dtos.UserActionDTO userActionDTO;

    abstract void generate() throws UserActionNotLoadedException;

    public AbstractReportGenerator setUserAction(UserAction<UserActionDTO> userAction) {
        this.userAction = userAction;
        return this;
    }
}
