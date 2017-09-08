package com.tiza.process.common.support.config;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: Constant
 * Author: DIYILIU
 * Update: 2017-08-07 14:45
 */

public final class Constant {

    private final static Logger logger = LoggerFactory.getLogger(Constant.class);

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

        public final static String ROTATE_DIRECTION = "rotateDirection";
        public final static String ROTATE_SPEED = "rotateSpeed";
        public final static String FUEL_VOLUME = "fuelVolume";
    }

    public static void init(String file){
        logger.info("加载配置文件...");
        initSqlCache(file);
    }

    public enum SQL{
        ;
        public final static String SELECT_VEHICLE_INFO = "selectVehicleInfo";
        public final static String UPDATE_VEHICLE_GPS_INFO = "updateVehicleGpsInfo";
        public final static String UPDATE_VEHICLE_WORK_PARAMETER = "updateVehicleGpsInfoWithWorkParameter";
    }


    private static Map<String, String> sqlCache = new HashMap<>();
    public static String getSQL(String sqlId) {
        return sqlCache.get(sqlId);
    }

    public static void initSqlCache(String sqlFile) {
        sqlCache.clear();

        InputStream is = null;
        try {
            is = new ClassPathResource(sqlFile).getInputStream();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(is);

            List<Node> sqlList = document.selectNodes("root/sql");
            for (Node sqlNode : sqlList) {
                String id = sqlNode.valueOf("@id");
                String content = sqlNode.getText().trim();
                sqlCache.put(id, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
