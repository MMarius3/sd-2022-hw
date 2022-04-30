package model.builder;

import model.Action;

import java.time.LocalDate;

public class ActionBuilder {
    private final Action action;

    public ActionBuilder() {
        action = new Action();
    }

    public ActionBuilder setID(Long id) {
        action.setId(id);
        return this;
    }

    public ActionBuilder setType(String type) {
        action.setType(type);
        return this;
    }

    public ActionBuilder setDetails(String details) {
        action.setDetails(details);
        return this;
    }

    public ActionBuilder setDate(LocalDate date) {
        action.setDate(date);
        return this;
    }

    public ActionBuilder setUserID(int id) {
        action.setUserID(id);
        return this;
    }

    public Action build() {
        return this.action;
    }
}
