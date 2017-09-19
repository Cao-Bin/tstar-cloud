package com.tiza.process.common.model;

import java.util.Date;

/**
 * Description: InOutRecord
 * 车辆进出仓库记录
 * Author: DIYILIU
 * Update: 2017-09-19 14:05
 */
public class InOutRecord {

    private int vehicleId;
    private int storehouseId;

    private Date gpsTime;
    private double lng;
    private double lat;

    // 0:在外; 1:在内
    private int status;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getStorehouseId() {
        return storehouseId;
    }

    public void setStorehouseId(int storehouseId) {
        this.storehouseId = storehouseId;
    }

    public Date getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(Date gpsTime) {
        this.gpsTime = gpsTime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
