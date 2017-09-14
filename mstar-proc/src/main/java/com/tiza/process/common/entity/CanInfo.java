package com.tiza.process.common.entity;

import java.util.Map;

/**
 * Description: CanInfo
 * Author: DIYILIU
 * Update: 2016-04-21 11:25
 */
public class CanInfo {

    private String softVersion;
    private String softName;
    private String modelCode;
    private String functionXml;
    private String alarmXml;

    private Map<String, CanPackage> canPackages;
    private Map emptyValues;
    // packageId 长度（占字节数）
    private int pidLength;

    public CanInfo() {

    }

    public CanInfo(String softVersion, String softName, String modelCode, String functionXml , String alarmXml) {
        this.softVersion = softVersion;
        this.softName = softName;
        this.modelCode = modelCode;
        this.functionXml = functionXml;
        this.alarmXml=alarmXml;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getFunctionXml() {
        return functionXml;
    }

    public void setFunctionXml(String functionXml) {
        this.functionXml = functionXml;
    }

    public Map getCanPackages() {
        return canPackages;
    }

    public void setCanPackages(Map canPackages) {
        this.canPackages = canPackages;
    }

    public int getPidLength() {
        return pidLength;
    }

    public void setPidLength(int pidLength) {
        this.pidLength = pidLength;
    }

    public Map getEmptyValues() {
        return emptyValues;
    }

    public void setEmptyValues(Map emptyValues) {
        this.emptyValues = emptyValues;
    }

    public String getAlarmXml() {
        return alarmXml;
    }

    public void setAlarmXml(String alarmXml) {
        this.alarmXml = alarmXml;
    }
}
