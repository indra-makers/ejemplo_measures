package com.indramakers.example.measuresms.model.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "[A-Z]{3}-[0-9]{3}")
    private String name;

    @Column(name = "branch_device")
    @NotBlank
    @NotEmpty
    @Length(min = 3, max = 20)
    private String branch;

    @Column(name = "measure_unit")
    @Pattern(regexp = "[A-Z]{3}")
    private String units;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


    @Column(name = "id_location")
    private Long idLocation;

    public Device() {
    }

    public Device(Long id, String name, String branch, String units, Date createdAt,Long IdLocation) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.units = units;
        this.createdAt = createdAt;
        this.idLocation=IdLocation;
    }

    public Device(String name, String branch, String units,Long IdLocation) {
        this.name = name;
        this.branch = branch;
        this.units = units;
        createdAt = new Date();
        this.idLocation=IdLocation;
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

    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
      this.idLocation = idLocation;
    }
}
