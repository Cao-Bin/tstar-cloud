package com.tiza.process.common.support.dao;

import com.tiza.process.common.support.config.Constant;
import com.tiza.process.common.support.dao.base.BaseDao;
import com.tiza.process.common.support.entity.VehicleInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: VehicleDao
 * Author: DIYILIU
 * Update: 2017-08-07 14:58
 */

@Component
public class VehicleDao extends BaseDao {

    public List<VehicleInfo> selectVehicleInfo() {
        String sql = Constant.getSQL(Constant.SQL.SELECT_VEHICLE_INFO);

        if (jdbcTemplate == null){

            return null;
        }

        return jdbcTemplate.query(sql, new RowMapper<VehicleInfo>() {

            @Override
            public VehicleInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                VehicleInfo vehicleInfo = new VehicleInfo();
                vehicleInfo.setId(rs.getInt("id"));
                vehicleInfo.setVin(rs.getString("vin"));
                vehicleInfo.setIccid(rs.getString("iccid"));

                return vehicleInfo;
            }
        });
    }
}
