package service.action;

import model.ActionEmployee;

import java.util.List;

public interface ActionService {

    List<ActionEmployee> findActionForClient();

    void addAction(ActionEmployee action);
}
