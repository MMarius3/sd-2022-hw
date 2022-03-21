package repository.action;

import model.Action;

import java.time.LocalDate;
import java.util.List;

public interface ActionRepository {

    List<Action> findActionsBetweenDates(String date1, String date2, Long id);

    boolean save(Action action);
}
