package model;

import java.util.Date;

public class EmployeeActivity {
    private Long id;
    private Long employeeId;
    private Long actionId;
    private Date dateOfAction;

    public EmployeeActivity(Long employeeId, Long actionId, Date dateOfAction) {
        this.employeeId = employeeId;
        this.actionId = actionId;
        this.dateOfAction = dateOfAction;
    }

    public EmployeeActivity(Long id, Long employeeId, Long actionId, Date dateOfAction) {
        this.id = id;
        this.employeeId = employeeId;
        this.actionId = actionId;
        this.dateOfAction = dateOfAction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Date getDateOfAction() {
        return dateOfAction;
    }

    public void setDateOfAction(Date dateOfAction) {
        this.dateOfAction = dateOfAction;
    }
}
