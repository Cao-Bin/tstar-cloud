package com.tiza.process.common.model;

/**
 * Description: VehicleInfo
 * Author: DIYILIU
 * Update: 2017-08-07 14:33
 */
public class VehicleInfo {

    private long id;
    private long terminalId;

    private String deviceId;
    private String terminalNo;
    private String sim;

    private String protocolType;

    // 车辆类型(1:泵车，2:搅拌车，3:商务车，4:物流车)
    private int vehicleType;
    // 超速阈值
    private int overSpeed;
    // 停留时间阈值(单位:分钟)
    private int stayTime;
    // 停留距离阈值(单位:米)
    private int stayDistance;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(long terminalId) {
        this.terminalId = terminalId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getOverSpeed() {
        return overSpeed;
    }

    public void setOverSpeed(int overSpeed) {
        this.overSpeed = overSpeed;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public int getStayDistance() {
        return stayDistance;
    }

    public void setStayDistance(int stayDistance) {
        this.stayDistance = stayDistance;
    }
}
