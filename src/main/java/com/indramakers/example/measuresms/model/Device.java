package com.indramakers.example.measuresms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_devices")
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_device")
    private Long id;

    @Column(name = "name_device")
    private String name;

    @Column(name = "branch_device")
    private String branch;

    @Column(name = "measure_unit")
    private String units;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Device() {
    }

    public Device(Long id, String name, String branch, String units, Date createdAt) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.units = units;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
