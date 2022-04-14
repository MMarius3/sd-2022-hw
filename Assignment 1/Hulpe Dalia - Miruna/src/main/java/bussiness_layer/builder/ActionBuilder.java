package bussiness_layer.builder;

import bussiness_layer.models.Action;

import java.sql.Date;

public class ActionBuilder {
    private Action action;

    public ActionBuilder() {
        action = new Action();
    }

    public ActionBuilder setType(String type) {
        action.setType(type);
        return this;
    }

    public ActionBuilder setId(Long id) {
        action.setId(id);
        return this;
    }

    public ActionBuilder setDate(Date date) {
        action.setDate_of_creation(date);
        return this;
    }

    public ActionBuilder setUserId(Long id) {
        action.setUser_id(id);
        return this;
    }

    public Action build() {
        return action;
    }
}
