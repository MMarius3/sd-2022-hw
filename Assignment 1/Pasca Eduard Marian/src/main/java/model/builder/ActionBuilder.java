package model.builder;

import model.Action;

import javax.accessibility.AccessibleIcon;

public class ActionBuilder {
    private Action action;

    public ActionBuilder(){
        action = new Action();
    }

    public ActionBuilder setId(long id){
        action.setId(id);
        return this;
    }

    public ActionBuilder setType(String type){
        action.setType(type);
        return this;
    }

    public Action build(){
        return action;
    }
}
