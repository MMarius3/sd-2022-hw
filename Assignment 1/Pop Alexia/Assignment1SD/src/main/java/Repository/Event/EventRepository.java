package Repository.Event;

import Model.Event;
import javafx.collections.ObservableList;


public interface EventRepository {

    boolean save(Event event);
    ObservableList<Event> findByUsername(String username);
}
