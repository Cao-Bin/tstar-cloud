package com.tiza.process.common.dao;

import com.diyiliu.common.dao.BaseDao;
import com.tiza.process.common.config.MStarConstant;
import com.tiza.process.common.model.CanInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: CanDao
 * Author: DIYILIU
 * Update: 2016-04-21 11:31
 */

@Component
public class CanDao extends BaseDao {

    public List<CanInfo> selectCanInfo() {
        String sql = MStarConstant.getSQL(MStarConstant.SQL.SELECT_CAN_INFO);

        if (jdbcTemplate == null) {
            logger.warn("未装载数据源，无法连接数据库!");
            return null;
        }

        return jdbcTemplate.query(sql, new RowMapper<CanInfo>() {
            @Override
            public CanInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

                String softVersion = rs.getString("code");
                String softName = rs.getString("description");
                String modelCode = rs.getString("modelcode");

                Clob xmlClob = rs.getClob("xml");
                String functionXml = xmlClob == null? "": xmlClob.getSubString(1, (int) xmlClob.length());

                return new CanInfo(softVersion, softName, modelCode, functionXml);
            }
        });
    }
}
