package com.indramakers.example.measuresms.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable {

    private int id_location;

    private String name_location;

    private Date created_at;

    private Date update_at;

    public Location() {
    }

    public Location(String name_location) {
        this.name_location = name_location;
    }

    public int getId_location() {
        return id_location;
    }

    public void setId_location(int id_location) {
        this.id_location = id_location;
    }

    public String getName_location() {
        return name_location;
    }

    public void setName_location(String name_location) {
        this.name_location = name_location;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
}
