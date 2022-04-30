package model.builder;

import model.Activity;
import model.User;

import java.sql.Date;

public class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder(){
        this.activity = new Activity();
    }

    public ActivityBuilder setDescription(String description){
        activity.setDescription(description);
        return this;
    }

    public ActivityBuilder setEmployee(User user){
        activity.setEmployee(user);
        return this;
    }

    public ActivityBuilder setId(Integer id){
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setDate(Date date){
        activity.setDate(date);
        return this;
    }

    public Activity build(){
        return activity;
    }
}
