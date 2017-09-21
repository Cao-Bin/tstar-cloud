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

                vehicleInfo.setVehicleType(rs.getInt("vehicletype"));
                vehicleInfo.setOverSpeed(rs.getInt("overspeedthreshold"));
                vehicleInfo.setStayTime(rs.getInt("staytimethreshold"));
                vehicleInfo.setStayDistance(rs.getInt("staydistancethreshold"));

                return vehicleInfo;
            }
        });
    }
}
