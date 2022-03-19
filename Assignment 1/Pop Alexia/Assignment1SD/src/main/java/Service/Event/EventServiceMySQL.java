package Service.Event;

import Model.Builder.EventBuilder;
import Model.Event;
import Repository.Event.EventRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class EventServiceMySQL implements EventService {

    private final EventRepository eventRepository;

    public EventServiceMySQL(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public boolean addEvent(String username,String action) {
        Event e = new EventBuilder()
                .setUsername(username)
                .setAction(action)
                .setDate(Date.valueOf(LocalDate.now()))
                .build();
        return eventRepository.save(e);
    }

    @Override
    public ObservableList<Event> findByUsernameAndDates(Date d1, Date d2, String username) {
       ObservableList<Event> events;
       events = eventRepository.findByUsername(username);
       return events.stream().filter(e-> (e.getDate().after(d1) && e.getDate().before(d2)) || e.getDate().equals(d1) || e.getDate().equals(d2)).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

}
