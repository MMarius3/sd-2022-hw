package service.action;

import model.ActionEmployee;
import repository.action.ActionRepository;

import java.util.List;

public class ActionServiceMySQL implements ActionService{

    private final ActionRepository actionRepository;

    public ActionServiceMySQL(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public List<ActionEmployee> findActionForClient() {
        return actionRepository.findActionForClient();
    }

    @Override
    public void addAction(ActionEmployee action) {
            actionRepository.addAction(action);
    }
}
