package com.diyiliu.common.util;


import com.diyiliu.common.model.Point;

/**
 * Description: GpsCorrectUtil
 * Author: DIYILIU
 * Update: 2016-04-14 14:35
 * <p>
 * 适用于google,高德体系的地图
 */
public class GpsCorrectUtil {

    final static double pi = 3.14159265358979324;
    final static double a = 6378245.0;
    final static double ee = 0.00669342162296594323;

    private static Object lock = new Object();

    public static Point transform(double wgLat, double wgLon) {
        if (outOfChina(wgLat, wgLon)) {
            return null;
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double lat = wgLat + dLat;
        double lng = wgLon + dLon;

        return new Point(lng, lat);
    }

    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 计算两点之间的距离
     *
     * @param p1
     * @param p2
     * @return double 单位(千米)
     */
    public static double distanceP2P(Point p1, Point p2) {
        synchronized (lock) {
            double alat = Math.abs(p1.getLat());
            double alon = Math.abs(p1.getLng());
            double blat = Math.abs(p2.getLat());
            double blon = Math.abs(p2.getLng());
            // 经纬度计算地球上任意两点间的距离的算法原理
            /*
             * 地球是一个近乎标准的椭球体，它的赤道半径为6378.137千米，极半径为6356.755千米，平均半径6371.004千米。
			 * 程序中我们假设地球是一个完美的球体，取其半径R=6378.137。
			 * 程序中以0度经线为基准，根据地球表面任意两点的经纬度计算出这两点间的地表距离
			 * （这里忽略地球表面地形对计算带来的误差，仅仅是理论上的估算值）。 设第一点A的经纬度为A(LonA,
			 * LatA)，第二点B的经纬度为B(LonB, LatB)；
			 * 按照0度经线的基准，东经取经度的正值(Longitude)，西经取经度负值(-Longitude
			 * )，北纬取90-纬度值(90-Latitude)，
			 * 南纬取90+纬度值(90+Latitude)，则经过上述处理过后的两点被计为(MLonA, MLatA)和(MLonB,
			 * MLatB)。那么根据三角推导，可以得到计算两点距离的如下公式：
			 * C=arccos(sin(LatA)*sin(LatB)*cos(LonA-LonB)+cos(LatA)*cos(LatB))
			 */
            // 如果仅对经度作正负的处理，而不对纬度作90-Latitude(假设都是北半球，南半球只有澳洲具有应用意义)的处理，那么公式将是：
            // C=sin(LatA)*sin(LatB) + cos(LatA)*cos(LatB)*cos(MLonA-MLonB)
            double R = 6378.137;// 地球半径(千米)
            // 判断点是在北半球还是南半球，本程序中输入的数据若为负则表示在南边球
            double distance = 0.0D;

            double _alat = (alat) * (Math.PI / 180); // 弧度
            double _alon = (alon) * (Math.PI / 180);
            double _blat = (blat) * (Math.PI / 180);
            double _blon = (blon) * (Math.PI / 180);

            double c = Math.sin(_alat) * Math.sin(_blat) + Math.cos(_alat) * Math.cos(_blat) * Math.cos(_alon - _blon); // Java中三角函数角度以弧度制表示
            if (c > 1) {
                c = 1;
            }
            distance = Math.acos(c) * R; // 弧长公式：弧长 = 弧度 * 半径
            if (distance <= 0.01) { // GPS误差
                distance = 0.0D;
            }
            return distance;
        }
    }


/*
    百度地图转换

    其中 bd_encrypt 将 GCJ-02 坐标转换成 BD-09 坐标， bd_decrypt 反之。
    const double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    void bd_encrypt(double gg_lat, double gg_lon, double &bd_lat, double &bd_lon)
    {
        double x = gg_lon, y = gg_lat;
        double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
        bd_lon = z * cos(theta) + 0.0065;
        bd_lat = z * sin(theta) + 0.006;
    }
    void bd_decrypt(double bd_lat, double bd_lon, double &gg_lat, double &gg_lon)
    {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
        gg_lon = z * cos(theta);
        gg_lat = z * sin(theta);
    }
*/
}
