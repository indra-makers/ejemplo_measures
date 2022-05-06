package com.indramakers.example.measuresms.model.entities;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable {

    private int id;
    private String name;
    private Date created_at;

    public Location(){
    }

    public Location(int id, String name){
        this.id=id;
        this.name=name;
        created_at= new Date();
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
