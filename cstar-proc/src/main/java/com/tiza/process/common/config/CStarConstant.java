package com.tiza.process.common.config;

import com.diyiliu.common.util.ConstantUtil;

/**
 * Description: CStarConstant
 * Author: DIYILIU
 * Update: 2017-09-20 16:54
 */
public final class CStarConstant extends ConstantUtil{


    public enum Kafka {
        ;
        public final static String TRACK_TOPIC = "trackTopic";
    }

    public enum FlowKey {
        ;
        public final static String POSITION = "position";
        public final static String STATUS = "status";
        public final static String PARAMETER = "parameter";
    }

    public enum Location{
        ;
        public final static String GPS_TIME = "gpsTime";
        public final static String SPEED = "speed";
        public final static String ALTITUDE = "altitude";
        public final static String DIRECTION = "direction";
        public final static String LOCATION_STATUS = "locationStatus";
        public final static String ACC_STATUS = "accStatus";
        public final static String ORIGINAL_LNG = "originalLng";
        public final static String ORIGINAL_LAT = "originalLat";
        public final static String LNG = "lng";
        public final static String LAT = "lat";
    }

    /**
     * accTime: 累计工作时间(小时)
     * rotateDirection：正反转
     * rotateSpeed：转速
     * fuelVolume：油位
     */
    public enum Parameter{
        ;
        public final static String ACC_HOUR = "accHour";
        public final static String ROTATE_DIRECTION = "rotateDirection";
        public final static String ROTATE_SPEED = "rotateSpeed";
        public final static String FUEL_VOLUME = "fuelVolume";
    }


    public enum SQL{
        ;
        public final static String SELECT_VEHICLE_INFO = "selectVehicleInfo";
        public final static String SELECT_VEHICLE_STOREHOUSE = "selectVehicleStorehouse";
        public final static String SELECT_VEHICLE_OUT_IN = "selectVehicleOutIn";
    }
}
