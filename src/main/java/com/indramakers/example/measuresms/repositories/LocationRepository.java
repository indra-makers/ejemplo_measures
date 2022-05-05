package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;

import com.indramakers.example.measuresms.model.entities.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class LocationRowMapper  implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("id_location"));
        location.setName(rs.getString("name_location"));
        return location;
    }
}

@Repository
public class LocationRepository {
    @Autowired
    private JdbcTemplate template;

    public void create(Location location){
        template.update("INSERT INTO tb_location(id_location, name_location) values(?,?)",
          location.getId(), location.getName());
    }

    public List<Location> findById(int id) {
        return template.query(
                "SELECT id_location, name_location FROM tb_location WHERE id_location=?",
                new LocationRowMapper() ,
                id);
    }

    public void deleteLocation(int id) {

        template.update(
            "DELETE  FROM tb_location WHERE id_location=?",
            id);

    }
}