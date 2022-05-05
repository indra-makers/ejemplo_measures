package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Locations;
import com.indramakers.example.measuresms.model.entities.Measure;

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

	public int delete(int id) {

		return template.update("DELETE FROM tb_locations where id_location=?", id);

	}

	public List<Measure> getMeasureByLocation(int locationId) {
		return template.query(
				"SELECT _medidas.value, device_id, date_time from tb_measures _medidas INNER JOIN tb_devices _dispositivos ON _dispositivos.id_device = _medidas.device_id INNER JOIN tb_locations _locations ON _dispositivos.location_id = _locations.id_location where _locations.id_location=?",
				new MeasureRowMapper(), locationId);
	}

}
