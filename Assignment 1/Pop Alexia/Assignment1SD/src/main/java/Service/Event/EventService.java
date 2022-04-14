package Service.Event;

import Model.Event;
import javafx.collections.ObservableList;

import java.sql.Date;

public interface EventService {

    boolean addEvent(String username,String action);
    ObservableList<Event> findByUsernameAndDates(Date d1, Date d2,String username);
}
