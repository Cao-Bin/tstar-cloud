package com.tiza.op.model;

import java.util.Date;

/**
 * Description: Position
 * Author: DIYILIU
 * Update: 2017-09-26 14:22
 */
public class Position {

    private Double lngD;
    private Double latD;

    private Double enLngD;
    private Double enLatD;

    private Date dateTime;

    // 里程
    private Double mileage;

    public Double getLngD() {
        return lngD;
    }

    public void setLngD(Double lngD) {
        this.lngD = lngD;
    }

    public Double getLatD() {
        return latD;
    }

    public void setLatD(Double latD) {
        this.latD = latD;
    }

    public Double getEnLngD() {
        return enLngD;
    }

    public void setEnLngD(Double enLngD) {
        this.enLngD = enLngD;
    }

    public Double getEnLatD() {
        return enLatD;
    }

    public void setEnLatD(Double enLatD) {
        this.enLatD = enLatD;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
}
