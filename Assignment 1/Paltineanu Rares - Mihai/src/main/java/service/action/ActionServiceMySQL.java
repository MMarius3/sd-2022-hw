package service.action;

import model.Action;
import repository.action.ActionRepository;

import java.sql.Date;

public class ActionServiceMySQL implements ActionService {
    private ActionRepository actionRepository;

    public ActionServiceMySQL(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public boolean save(Action action) {
        return actionRepository.save(action);
    }

    @Override
    public boolean filterByDateAndId(Long user_id, Date from, Date to) {
        return false;
    }
}
