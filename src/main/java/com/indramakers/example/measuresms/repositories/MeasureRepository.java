package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


class MeasureRowMapper implements RowMapper<Measure> {
    @Override
    public Measure mapRow(ResultSet rs, int rowNum) throws SQLException {
        Measure measure = new Measure();
        measure.setDeviceId(rs.getLong("device_id"));
        measure.setId(rs.getLong("id"));
        measure.setValue(rs.getDouble("value"));
        measure.setDateTime(rs.getDate("date_time"));
        return measure;
    }
}

@Repository
public class MeasureRepository {

    @Autowired
    private JdbcTemplate template;

    public void create(Measure measure) {
        template.update("INSERT INTO tb_measures(value, device_id) values(?,?)",
               measure.getValue(), measure.getDeviceId());
    }

    public List<Measure> findByDevice(Long deviceId) {
        return template.query(
                "SELECT id, date_time, value, device_id FROM tb_measures WHERE device_id=?",
                new MeasureRowMapper() ,
                deviceId);
    }

    public List<Measure> findByLocation(Long idLocation) {
        return template.query(
                "SELECT tb_measures.* FROM tb_devices join tb_measures on tb_devices.id_device = tb_measures.device_id join tb_location on tb_devices.id_location=?",
                new MeasureRowMapper() ,
                idLocation);
    }
}
