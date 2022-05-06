package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

 class LocationRowMapper implements RowMapper<Location>{

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("id"));
        location.setName(rs.getString("name"));
        location.setCreated_at(rs.getDate("created_at"));
        return location;
    }
}

@Repository
public class LocationRepository {

    //componente de spring para acceder a la BD
    @Autowired
    private JdbcTemplate template;

    public void create(Location location){
        //uso de update para crear, modificar y elminar
        template.update("INSERT INTO tbl_locations(name) values (?)",
                location.getName());
    }

    public List<Location> findByLocation_id(int location_id){
        //uso de query solo consultas
        return template.query(
                "SELECT id, name, created_at FROM tbl_locations WHERE id=?",
                new LocationRowMapper(),
                location_id );
    }

    public void deleteLocation(int id) {
        template.update(
                "DELETE  FROM tbl_location WHERE id=?",
                id);
    }

}
