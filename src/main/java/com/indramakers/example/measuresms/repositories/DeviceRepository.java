package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class DevicesRowMapper implements RowMapper<Device> {
    @Override
    public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
        Device device = new Device();
        device.setId(rs.getLong("id_device"));
        device.setName(rs.getString("name_device"));
        device.setBranch(rs.getString("branch_device"));
        device.setUnits(rs.getString("measure_unit"));
        device.setLocationId(rs.getInt("id_location"));
        return device;
    }
}
@Repository
public class DeviceRepository {

    @Autowired
    private JdbcTemplate template;

    public List<Device> findByLocation(int id) {
        return template.query(
                "SELECT id_device, name_device, branch_device, measure_unit, id_location FROM tb_devices WHERE id_location=?",
                new DevicesRowMapper() ,
                id);
    }


}
