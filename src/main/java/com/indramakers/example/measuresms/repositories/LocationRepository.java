package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static java.time.LocalTime.now;

class LocationRowMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();
        location.setId_location(rs.getLong("id_location"));
        location.setName(rs.getString("name"));
        location.setCreated_at(rs.getDate("created_at"));
        location.setUpdated_at(rs.getDate("updated_at"));
        return location;
    }
}

@Repository
public class LocationRepository {


        @Autowired
        private JdbcTemplate template;

        public void createLocation(Location location) {
            Date date = new Date();

            template.update("INSERT INTO tb_locations(name, created_at) values(?,?)",
                    location.getName(), new Timestamp(date.getTime()));
        }

        public List<Location> findByLocationId(int id_location) {
            return template.query(
                    "SELECT id_location, name, created_at, updated_at FROM tb_locations WHERE id_location=?",
                    new LocationRowMapper() ,
                    id_location);
        }

        public List<Location> findByLocationName(String name) {
            return template.query(
                "SELECT id_location, name, created_at, updated_at FROM tb_locations WHERE name=?",
                new LocationRowMapper() ,
                name);
        }

        public void deleteLocation(int id_location){
            template.update("DELETE FROM tb_locations WHERE id_location = ?",
                    id_location);
        }

        public List<Double> getValueMeasure(int id_location){
            return template.query(
                    "SELECT tb_measures.value FROM tb_measures JOIN tb_devices ON tb_measures.device_id = tb_devices.id_device JOIN tb_locations ON tb_devices.id_location = tb_locations.id_location WHERE tb_locations.id_location = ?",
                    (rs, rowNum) ->
                            rs.getDouble("value"),
                    id_location);
        }

        public List<Measure> getMeasures(int id_location){
            return template.query(
                    "SELECT tb_measures.value, tb_measures.date_time, tb_measures.device_id, tb_measures.id FROM tb_measures JOIN tb_devices ON tb_measures.device_id = tb_devices.id_device JOIN tb_locations ON tb_devices.id_location = tb_locations.id_location WHERE tb_locations.id_location = ?",
                    new MeasureRowMapper(),
                    id_location);
        }

        public List<Location> getLocation(){
            return template.query(
                    "SELECT id_location, name, created_at, updated_at FROM tb_locations",
                    new LocationRowMapper());
        }

    }

