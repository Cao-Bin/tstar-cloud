package com.tiza.process.common.dao;

import com.diyiliu.common.dao.BaseDao;
import com.diyiliu.common.model.Area;
import com.diyiliu.common.model.Circle;
import com.diyiliu.common.model.Point;
import com.diyiliu.common.model.Region;
import com.diyiliu.common.util.JacksonUtil;
import com.tiza.process.common.config.MStarConstant;
import com.tiza.process.common.model.InOutRecord;
import com.tiza.process.common.model.Storehouse;
import com.tiza.process.common.model.VehicleInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: VehicleDao
 * Author: DIYILIU
 * Update: 2017-08-07 14:58
 */

@Component
public class VehicleDao extends BaseDao {

    public List<VehicleInfo> selectVehicleInfo() {
        String sql = MStarConstant.getSQL(MStarConstant.SQL.SELECT_VEHICLE_INFO);

        if (jdbcTemplate == null) {
            logger.warn("未装载数据源，无法连接数据库!");
            return null;
        }

        return jdbcTemplate.query(sql, new RowMapper<VehicleInfo>() {
            @Override
            public VehicleInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                VehicleInfo vehicleInfo = new VehicleInfo();
                vehicleInfo.setId(rs.getInt("id"));
                vehicleInfo.setTerminalId(rs.getLong("terminalid"));
                vehicleInfo.setDeviceId(rs.getString("deviceid"));
                vehicleInfo.setTerminalNo(rs.getString("terminalno"));
                vehicleInfo.setSim(rs.getString("simno"));
                vehicleInfo.setProtocolType(rs.getString("protocoltype"));

                return vehicleInfo;
            }
        });
    }

    public Map selectVehicleStorehouse() {
        String sql = MStarConstant.getSQL(MStarConstant.SQL.SELECT_VEHICLE_STOREHOUSE);

        if (jdbcTemplate == null) {
            logger.warn("未装载数据源，无法连接数据库!");
            return null;
        }

        Map map = new HashMap();
        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
            while (rs.next()) {
                int vehicleId = rs.getInt("vehicleid");
                int id = rs.getInt("id");
                int shape = rs.getInt("fencesharp");

                Clob content = (Clob) rs.getObject("fencegeoinfo");
                String str = content.getSubString(1, (int) content.length());

                Area area = null;
                switch (shape) {

                    // 圆形
                    case 1:
                        List<Circle> list = JacksonUtil.toList(str, Circle.class);
                        area = list.get(0);
                        break;
                    case 2:
                        List<Point> points = JacksonUtil.toList(str, Point.class);
                        area = new Region(points.toArray(new Point[points.size()]));
                        break;
                    default:
                }

                Storehouse storehouse = new Storehouse();
                storehouse.setArea(area);
                storehouse.setId(id);
                if (map.containsKey(vehicleId)) {

                    List list = (List) map.get(vehicleId);
                    list.add(storehouse);
                } else {

                    List list = new ArrayList();
                    list.add(storehouse);

                    map.put(vehicleId, list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<InOutRecord> selectInOutRecord(){
        String sql = MStarConstant.getSQL(MStarConstant.SQL.SELECT_VEHICLE_OUT_IN);

        if (jdbcTemplate == null) {
            logger.warn("未装载数据源，无法连接数据库!");
            return null;
        }

        return jdbcTemplate.query(sql, new RowMapper<InOutRecord>() {
            @Override
            public InOutRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                InOutRecord record = new InOutRecord();
                record.setVehicleId(rs.getInt("vehicleid"));
                record.setStorehouseId(rs.getInt("unitid"));
                record.setStatus(rs.getInt("status"));

                return record;
            }
        });
    }
}
