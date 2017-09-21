package com.tiza.process.common.model;

import com.diyiliu.common.util.CommonUtil;

import java.util.Map;

/**
 * Description: Parameter
 * Author: DIYILIU
 * Update: 2017-08-07 18:53
 */
public class Parameter {

    /** acc 累计工作时间*/
    private Long accTime;
    /** acc 累计工作时间(单位：小时)*/
    private Double accHour;

    private Integer gsmSignal;
    private Double voltage;
    private Integer satellite;
    private Map canValues;

    // 正反转
    private Integer rotateDirection;
    // 转速
    private Integer rotateSpeed;
    // 状态量
    private Integer stateVolume;
    // 油量
    private Integer fuelVolume;

    public Parameter() {

    }

    public Parameter(Long accTime, Integer gsmSignal, Double voltage, Integer satellite) {
        this.accTime = accTime;
        this.gsmSignal = gsmSignal;
        this.voltage = voltage;
        this.satellite = satellite;
    }

    public Long getAccTime() {
        return accTime;
    }

    public void setAccTime(Long accTime) {
        this.accTime = accTime;
    }

    public Double getAccHour() {
        if (accTime != null) {
            double d = accTime / 3600.0;
            accHour = CommonUtil.keepDecimal(d, 2);
        }
        return accHour;
    }

    public void setAccHour(Double accHour) {
        this.accHour = accHour;
    }

    public Integer getGsmSignal() {
        return gsmSignal;
    }

    public void setGsmSignal(Integer gsmSignal) {
        this.gsmSignal = gsmSignal;
    }

    public Double getVoltage() {
        if(voltage != null){
            double g =voltage/1000.0;
            voltage=CommonUtil.keepDecimal(g, 2);
        }
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Integer getSatellite() {
        return satellite;
    }

    public void setSatellite(Integer satellite) {
        this.satellite = satellite;
    }

    public Map getCanValues() {
        return canValues;
    }

    public void setCanValues(Map canValues) {
        this.canValues = canValues;
    }

    public Integer getRotateDirection() {
        return rotateDirection;
    }

    public void setRotateDirection(Integer rotateDirection) {
        this.rotateDirection = rotateDirection;
    }

    public Integer getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(Integer rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public Integer getStateVolume() {
        return stateVolume;
    }

    public void setStateVolume(Integer stateVolume) {
        this.stateVolume = stateVolume;
    }

    public Integer getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(Integer fuelVolume) {
        this.fuelVolume = fuelVolume;
    }
}
