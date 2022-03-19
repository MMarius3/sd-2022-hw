package repository.action;

import model.Action;

import java.sql.Date;

public interface ActionRepository {

    boolean save(Action action);

    boolean filterByDateAndId(Long user_id, Date from, Date to);
}
