package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.time.LocalTime.now;

class LocationRowMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();
        location.setId_location(rs.getInt("id_location"));
        location.setName_location(rs.getString("name_location"));
        location.setCreated_at(rs.getDate("created_at"));
        location.setUpdate_at(rs.getDate("update_at"));
        return location;
    }
}

@Repository
public class LocationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void createLocation (Location location){
        jdbcTemplate.update("INSERT INTO tb_locations(name_location) values (?)",
                location.getName_location());
    }

    public List<Location> findByName(String name){
        return jdbcTemplate.query("SELECT id_location, name_location, created_at, update_at FROM public.tb_locations WHERE name_location =? ",
                new LocationRowMapper(), name);
    }

    public void deleteById(int id_location) {
        jdbcTemplate.update("DELETE FROM public.tb_locations WHERE id_location=?",
                id_location);
    }

    public List<Measure> findMeasuresByLocation(int id_location){
        return jdbcTemplate.query("select tb_measures.id, tb_measures.date_time, tb_measures.value, tb_measures.device_id  from tb_locations, tb_measures, tb_devices where tb_locations.id_location = tb_devices.fk_id_location and tb_locations.id_location=?",
                new MeasureRowMapper(), id_location);
    }


}
