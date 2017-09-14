package com.tiza.process.common.entity;

import java.util.List;
import java.util.Map;

/**
 * Description: CanPackage
 * Author: DIYILIU
 * Update: 2016-04-21 16:44
 */

public class CanPackage {

    private String packageId;
    private int length;
    private List<NodeItem> itemList;
    private Map emptyValues;

    public CanPackage() {
    }

    public CanPackage(String packageId, int length) {
        this.packageId = packageId;
        this.length = length;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<NodeItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<NodeItem> itemList) {
        this.itemList = itemList;
    }

    public Map getEmptyValues() {
        return emptyValues;
    }

    public void setEmptyValues(Map emptyValues) {
        this.emptyValues = emptyValues;
    }

    public int getIdLength() {
        return packageId.length() / 2;
    }
}
