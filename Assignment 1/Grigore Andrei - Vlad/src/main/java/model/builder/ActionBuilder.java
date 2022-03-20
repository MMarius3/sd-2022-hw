package model.builder;

import model.Action;
import model.User;

public class ActionBuilder {
    private Action action;

    public ActionBuilder(){action = new Action();}

    public ActionBuilder setId(Long id){
        action.setId(id);
        return this;
    }

    public Action build(){return action;}

    public ActionBuilder setType(String type){
        action.setType(type);
        return this;
    }

    public ActionBuilder setDescription(String description){
        action.setDescription(description);
        return this;
    }

    public ActionBuilder setUser(User user){
        action.setUser(user);
        return this;
    }
}
