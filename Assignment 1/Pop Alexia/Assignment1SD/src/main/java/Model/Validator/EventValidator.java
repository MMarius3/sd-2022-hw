package Model.Validator;

import Repository.Event.EventRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EventValidator {
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    private final List<String> errors = new ArrayList<>();
    private final EventRepository eventRepository;

    public EventValidator(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void validate(String username, String dateFrom,String dateTo) {
        errors.clear();
        if(username.equals(""))
            errors.add("Enter an employee username");
        else if (eventRepository.findByUsername(username) == null)
            errors.add("Employee with this username does not exist");
        else if(dateFrom.equals("") || dateTo.equals(""))
            errors.add("Enter two dates");
        else{
           validateDate(dateFrom);
           validateDate(dateTo);
           if(errors.isEmpty()) {
               if (Date.valueOf(dateFrom).after(Date.valueOf(dateTo)))
                   errors.add("Date From is after Date To");
           }
        }
    }

    public void validateDate(String date){
        if(!date.matches(DATE_REGEX))
            errors.add("Invalid date");
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
