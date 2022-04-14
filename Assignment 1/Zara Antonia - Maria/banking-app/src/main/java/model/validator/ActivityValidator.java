package model.validator;

import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ActivityValidator {

    private static final String DATE_VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final Integer MAX_DESCRIPTION_LENGTH = 100;

    private final List<String> errors = new ArrayList<>();
    private final UserRepository userRepository;

    public ActivityValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(Integer id, String description, String date){
        errors.clear();
        validateEmployeeId(id);
        validateDescription(description);
        validateDate(date);
    }

    private void validateEmployeeId(Integer id){
        if(id == null){
            errors.add("id must be filled");
        }
        else
        if(userRepository.findById(id).isEmpty()){
            errors.add("Employee ID unmatched");
        }
    }

    private void validateDescription(String description){
        if(description.equals("") || description == null){
            errors.add("description must be filled");
        }
        else
        if(description.length() > MAX_DESCRIPTION_LENGTH){
            errors.add("Description is too long");
        }
    }

    private void validateDate(String date){
        if(date.equals("") || date == null){
            errors.add("date must be filled");
        }
        else
        if(!date.matches(DATE_VALIDATION_REGEX)){
            errors.add("Date must be of type YYYY-MM-DD");
        }
    }

    public String getValidateDateError(String date){
        if(date.equals("") || date == null){
            return "date must be filled";
        }
        else
        if(!date.matches(DATE_VALIDATION_REGEX)){
            return "Date must be of type YYYY-MM-DD";
        }
        return "";
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
