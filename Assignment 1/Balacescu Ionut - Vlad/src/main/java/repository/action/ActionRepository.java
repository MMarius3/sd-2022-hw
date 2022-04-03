package repository.action;

import model.ActionEmployee;

import java.util.List;

public interface ActionRepository {

    List<ActionEmployee> findActionForClient();

    void addAction(ActionEmployee action);
}
