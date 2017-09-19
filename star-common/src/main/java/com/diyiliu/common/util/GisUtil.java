package com.diyiliu.common.util;

import com.diyiliu.common.model.Point;

/**
 * Description: GisUtil
 * 地理信息处理工具类
 * Author: DIYILIU
 * Update: 2017-09-19 09:32
 */
public class GisUtil {
    private static Object lock = new Object();

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

            /*
             * 经纬度计算地球上任意两点间的距离的算法原理
             * 地球是一个近乎标准的椭球体，它的赤道半径为6378.137千米，极半径为6356.755千米，平均半径6371.004千米。
			 * 程序中我们假设地球是一个完美的球体，取其半径R=6378.137。
			 * 程序中以0度经线为基准，根据地球表面任意两点的经纬度计算出这两点间的地表距离
			 * （这里忽略地球表面地形对计算带来的误差，仅仅是理论上的估算值）。 设第一点A的经纬度为A(LonA,
			 * LatA)，第二点B的经纬度为B(LonB, LatB)；
			 * 按照0度经线的基准，东经取经度的正值(Longitude)，西经取经度负值(-Longitude)，北纬取90-纬度值(90-Latitude)，
			 * 南纬取90+纬度值(90+Latitude)，则经过上述处理过后的两点被计为(MLonA, MLatA)和(MLonB,MLatB)。
			 * 那么根据三角推导，可以得到计算两点距离的如下公式：
			 * C=arccos(sin(LatA)*sin(LatB)*cos(LonA-LonB)+cos(LatA)*cos(LatB))
			 */

            // 如果仅对经度作正负的处理，而不对纬度作90-Latitude(假设都是北半球，南半球只有澳洲具有应用意义)的处理，那么公式将是：
            double R = 6378.137;// 地球半径(千米)

            // 判断点是在北半球还是南半球，本程序中输入的数据若为负则表示在南边球
            double distance;
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
}
