package model;

import java.sql.Timestamp;

public class Activity {

    private String name;
    private int id;
    private Timestamp created_at;

    public Activity(int id, String name, Timestamp created_at){
        this.id = id;
        this.name = name;
        this.created_at = created_at;
    }

    public Activity(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
