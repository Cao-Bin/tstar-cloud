package com.tiza.process.common.config;

import com.diyiliu.common.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: EStarConstant
 * Author: DIYILIU
 * Update: 2017-08-07 14:45
 */

public final class EStarConstant extends ConstantUtil {
    private final static Logger logger = LoggerFactory.getLogger(EStarConstant.class);

    public enum Kafka {
        ;
        public final static String TRACK_TOPIC = "trackTopic";
    }

    public static void init(String file){
        logger.info("init configuration...");
        initSqlCache(file);
    }

    public enum SQL{
        ;
        public final static String SELECT_VEHICLE_INFO = "selectVehicleInfo";
    }
}
