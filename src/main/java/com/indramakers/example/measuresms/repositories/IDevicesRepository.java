package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDevicesRepository extends CrudRepository<Device, Long> {

    //select * from tb_devices where branch_devices = ?
    //findBy{{nombreAtributo}}(tipoAtributo {{nombreAtributo}}
    List<Device> findByBranch(String branch);

    //consultas JPQL
    //SELECT obj FROM Class obj WHERE {{predicados}},
    // los predicados son en base a los atributos de la clase
    @Query("SELECT dev FROM Device dev WHERE dev.name = :name")
    List<Device> findByName(String name);

    @Query("SELECT dev FROM Device dev WHERE dev.location= :location")
    List<Device> findByLocation(int location);
}
