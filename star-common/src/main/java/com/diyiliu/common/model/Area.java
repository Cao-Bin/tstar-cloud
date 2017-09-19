package com.diyiliu.common.model;

/**
 * Description: Area
 * Author: DIYILIU
 * Update: 2017-09-19 09:27
 */
public interface Area {

    /**
     * 是否在区域内
     *
     * @param point
     * @return (0: 外, 1: 内)
     */
    public int isPointInArea(Point point);
}
