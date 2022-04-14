package services.action;

import model.Action;
import repository.action.ActionRepository;

import java.time.LocalDate;
import java.util.List;

public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;

    public ActionServiceImpl(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public List<Action> findActionsBetweenDates(String date1, String date2, Long id) {
        return actionRepository.findActionsBetweenDates(date1, date2, id);
    }

    @Override
    public boolean save(Action action) {
        return actionRepository.save(action);
    }

    private String getStringFromDate(LocalDate date) {
        String d = "";
        d += date.getYear();
        d += "-";
        d += date.getMonth();
        d += "-";
        d += date.getDayOfMonth();

        return d;
    }
}
