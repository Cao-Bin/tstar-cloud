package com.tiza.process.common.entity;

/**
 * Description: Status
 * Author: DIYILIU
 * Update: 2017-08-03 17:53
 */
public class Status {

    private int location;
    private int lat;
    private int lng;
    private int acc;
    private int lock;

    private int discontinue;
    private int powerOff;
    private int lowPower;
    private int changeSIM;
    private int gpsFault;
    private int loseAntenna;
    private int aerialCircuit;
    private int powerDefence;
    private int overSpeed;
    private int trailer;
    private int uncap;

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public int getDiscontinue() {
        return discontinue;
    }

    public void setDiscontinue(int discontinue) {
        this.discontinue = discontinue;
    }

    public int getPowerOff() {
        return powerOff;
    }

    public void setPowerOff(int powerOff) {
        this.powerOff = powerOff;
    }

    public int getLowPower() {
        return lowPower;
    }

    public void setLowPower(int lowPower) {
        this.lowPower = lowPower;
    }

    public int getChangeSIM() {
        return changeSIM;
    }

    public void setChangeSIM(int changeSIM) {
        this.changeSIM = changeSIM;
    }

    public int getGpsFault() {
        return gpsFault;
    }

    public void setGpsFault(int gpsFault) {
        this.gpsFault = gpsFault;
    }

    public int getLoseAntenna() {
        return loseAntenna;
    }

    public void setLoseAntenna(int loseAntenna) {
        this.loseAntenna = loseAntenna;
    }

    public int getAerialCircuit() {
        return aerialCircuit;
    }

    public void setAerialCircuit(int aerialCircuit) {
        this.aerialCircuit = aerialCircuit;
    }

    public int getPowerDefence() {
        return powerDefence;
    }

    public void setPowerDefence(int powerDefence) {
        this.powerDefence = powerDefence;
    }

    public int getOverSpeed() {
        return overSpeed;
    }

    public void setOverSpeed(int overSpeed) {
        this.overSpeed = overSpeed;
    }

    public int getTrailer() {
        return trailer;
    }

    public void setTrailer(int trailer) {
        this.trailer = trailer;
    }

    public int getUncap() {
        return uncap;
    }

    public void setUncap(int uncap) {
        this.uncap = uncap;
    }
}
