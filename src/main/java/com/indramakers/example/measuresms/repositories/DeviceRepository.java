package com.indramakers.example.measuresms.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Locations;

class DeviceRowMapper implements RowMapper<Device> {
	@Override
	public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
		Device dev = new Device();
		dev.setName(rs.getString("name_device"));
		dev.setBranch(rs.getString("branch_device"));
		dev.setUnits(rs.getString("measure_unit"));
		dev.setIdLocation(rs.getInt("location_id"));
		return dev;
	}
}

@Repository
public class DeviceRepository {

	@Autowired
	private JdbcTemplate template;



	public void create(Device device) {
		template.update("INSERT INTO tb_devices(name_device,branch_device,measure_unit,location_id) values(?,?,?,?)", device.getName(),
				device.getBranch(), device.getUnits(),device.getIdLocation());
	}

	public List<Device> getDeviceByLocation(int idLocation) {
		return template.query("SELECT  location_id, name_device, branch_device, measure_unit FROM tb_devices WHERE location_id = ?",
				new DeviceRowMapper(), idLocation);
	}

}
