package Model.Builder;

import Model.Action;

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

    public ActionBuilder setActivity(String activity){
        action.setActivity(activity);
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
