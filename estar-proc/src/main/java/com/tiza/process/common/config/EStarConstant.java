package com.tiza.process.common.config;

import com.diyiliu.common.util.ConstantUtil;

/**
 * Description: EStarConstant
 * Author: DIYILIU
 * Update: 2017-08-07 14:45
 */

public final class EStarConstant extends ConstantUtil {
    public enum Kafka {
        ;
        public final static String TRACK_TOPIC = "trackTopic";
    }

    public enum SQL{
        ;
        public final static String SELECT_VEHICLE_INFO = "selectVehicleInfo";
    }
}
