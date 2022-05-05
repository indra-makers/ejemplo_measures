package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

class LocationRowMapper implements RowMapper<Locations> {
	@Override
	public Locations mapRow(ResultSet rs, int rowNum) throws SQLException {
		Locations locations = new Locations();
		locations.setId(rs.getInt("id_location"));
		locations.setName(rs.getString("name"));
		return locations;
	}

}

@Repository
public class LocationRepository {

	@Autowired
	private JdbcTemplate template;

	public void create(Locations locations) {
		template.update("INSERT INTO tb_locations(name) values(?)", locations.getName());
	}

	public List<Locations> findById(int id) {
		return template.query("SELECT id_location, name from tb_locations where id_location=?", new LocationRowMapper(),
				id);

	}

	public int delete(int id)  {
		
		return template.update("DELETE FROM tb_locations where id_location=?", id);


	}

}
