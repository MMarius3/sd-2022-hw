package model.validation;

import java.util.List;
import java.util.stream.Collectors;

public class ResultFetchException extends RuntimeException {
    private final List<String> errors;

    public ResultFetchException(List<String> errors) {
        super("Unable to fetch the result => ResultFetchException");
        this.errors = errors;
    }

    @Override
    public String toString() {
        return errors.stream().map(Object::toString).collect(Collectors.joining(",")) + super.toString();
    }
}