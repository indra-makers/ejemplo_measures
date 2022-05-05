package com.indramakers.example.measuresms.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable {

    private Long id_location;
    private String name;
    private Date created_at;
    private Date updated_at;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public Long getId_location() {
        return id_location;
    }

    public void setId_location(Long id_location) {
        this.id_location = id_location;
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

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

}
