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

    public enum Location{
        ;
        public final static String GPS_TIME = "gpsTime";
        public final static String LOCATION_STATUS = "locationStatus";
        public final static String ORIGINAL_LNG = "originalLng";
        public final static String ORIGINAL_LAT = "originalLat";
        public final static String LNG = "lng";
        public final static String LAT = "lat";

        public final static String VEHICLE_ID = "vehicleId";
    }


    public enum SQL{
        ;
        public final static String SELECT_VEHICLE_INFO = "selectVehicleInfo";
    }
}
