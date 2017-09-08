package com.tiza.process.common.support.dao.base;

import com.tiza.process.common.support.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Description: BaseDao
 * Author: DIYILIU
 * Update: 2017-08-07 16:55
 */

public class BaseDao {

    private static Logger logger = LoggerFactory.getLogger(BaseDao.class);

    protected static JdbcTemplate jdbcTemplate;

    public static void initDataSource(DataSource dataSource) {
        if (jdbcTemplate == null) {
            logger.info("装载数据源...");
            jdbcTemplate = new JdbcTemplate(dataSource);
        }

        Constant.init("init-sql.xml");
    }

    public boolean update(String sql, Object[] values) {
        int result = jdbcTemplate.update(sql, values);

        if (result > 0) {

            return true;
        }

        return false;
    }
}
