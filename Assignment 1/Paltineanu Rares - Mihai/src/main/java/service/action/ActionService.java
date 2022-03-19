package service.action;

import model.Action;

import java.sql.Date;

public interface ActionService {
    boolean save(Action action);

    boolean filterByDateAndId(Long user_id, Date from, Date to);
}
