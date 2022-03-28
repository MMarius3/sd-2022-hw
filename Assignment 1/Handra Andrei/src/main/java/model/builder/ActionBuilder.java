package model.builder;


import model.Action;

import java.util.Date;

public class ActionBuilder {

    private Action action;

    public ActionBuilder(){
        action = new Action();
    }

    public ActionBuilder setId(Long id){
        action.setId(id);
        return this;
    }

    public ActionBuilder setUserId(Long userId){
        action.setUserId(userId);
        return this;
    }
    public ActionBuilder setRightId(Long rightId){
        action.setRightId(rightId);
        return this;
    }
    public ActionBuilder setRightName(String name){
        action.setRightName(name);
        return this;
    }
    public ActionBuilder setActionDate(Date date){
        action.setActionDate(date);
        return this;
    }

    public Action build(){
        return action;
    }
}
