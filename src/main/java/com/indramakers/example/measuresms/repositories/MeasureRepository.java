package com.indramakers.example.measuresms.repositories;

import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.model.responses.MeasureSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        measure.setDeviceId(rs.getString("device_id"));
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

    public Page<Measure> findByDevice(String deviceId, Pageable page) {
        if (page != null) {
            Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("id");
            List<Measure> users = template.query("SELECT id, date_time, value, device_id FROM tb_measures WHERE device_id=?" +
                            " ORDER BY " + order.getProperty() + " "
                            + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset(),
                    new MeasureRowMapper(), deviceId);
            return new PageImpl<Measure>(users, page, countByDevice(deviceId));
        } else {
            List<Measure> users = template.query("SELECT id, date_time, value, device_id FROM tb_measures WHERE device_id=?"
                    ,new MeasureRowMapper(), deviceId);
            return new PageImpl<Measure>(users, page, countByDevice(deviceId));
        }
    }


    public int countByDevice(String deviceId) {
        return template.queryForObject("SELECT count(1) FROM tb_measures WHERE device_id=?", Integer.class, deviceId);
    }

    public List<MeasureSummary> getSummary() {
        return template.query("select avg(value) average, device_id from tb_measures group by device_id",
                (rs, rn) -> new MeasureSummary(rs.getString("device_id"),
                           rs.getDouble("average")));
    }
}
