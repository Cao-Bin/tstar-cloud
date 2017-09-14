package com.diyiliu.common.model;

/**
 * Description: Point
 * Author: DIYILIU
 * Update: 2017-08-03 17:51
 */
public class Point {

    private double lng;
    private double lat;

    public Point() {
    }

    public Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


}
