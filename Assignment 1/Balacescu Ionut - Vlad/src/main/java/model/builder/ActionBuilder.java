package model.builder;


import model.ActionEmployee;

public class ActionBuilder {
    private ActionEmployee actionEmployee;

    public ActionBuilder() {
        actionEmployee = new ActionEmployee();
    }

    public ActionBuilder setId(long id){
        actionEmployee.setId(id);
        return this;
    }

    public ActionBuilder setUsername(String username){
        actionEmployee.setUsername(username);
       return this;
    }

    public ActionBuilder setName(String name){
        actionEmployee.setName(name);
        return this;
    }

    public ActionBuilder setDate(String date){
        actionEmployee.setDate(date);
        return this;
    }
    public ActionEmployee build() {
        return actionEmployee;
    }
}
