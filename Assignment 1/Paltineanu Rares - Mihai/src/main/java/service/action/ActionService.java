package service.action;

import model.Action;

import java.sql.Date;
import java.util.List;

public interface ActionService {
    boolean save(Action action);

    List<Action> filterByDateAndId(Long user_id, Date from, Date to);
}
